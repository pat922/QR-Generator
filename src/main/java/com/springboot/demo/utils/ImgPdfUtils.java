package com.springboot.demo.utils;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.imageio.ImageIO;

// @SuppressWarnings("unused")
public class ImgPdfUtils {

    public static void listOrder(String path) {

        File[] listFiles = new File(path).listFiles();
        TreeMap<Integer, File> tree = new TreeMap<Integer, File>();
        for(File f : listFiles)
        {
            tree.put(Integer.parseInt(f.getName().replaceAll(".jpg$", "")), f);
        }
//        for(Entry<Integer, File> eif : tree.entrySet())
//        {
//            System.out.println("[listOrder] " + eif.getKey()+"="+eif.getValue().toString());
//        }
    }
    /**
     * put multiple image into a pdf
     * @param list  file list
     * @param file  file path
     * @return true if combined success
     *   if file name not list 1.jpg，2.jpg，3.jpg，4.jpg, then need override the order function in TreeMap
     */
    public static boolean imgMerageToPdf(File[] list, File file) throws Exception {
        //order all files in fileList by TreeMap based on name.
        Map<Integer,File> mif = new TreeMap<Integer,File>();
        for(File f : list)
            mif.put(Integer.parseInt(f.getName().replaceAll(".jpg$", "")), f);

        //get  width and height of the first image as basic
        ByteArrayOutputStream baos = new ByteArrayOutputStream(2048*3);
        InputStream is = new FileInputStream(mif.get(1));
        for(int len;(len=is.read())!=-1;)
            baos.write(len);

        baos.flush();
        Image image = Image.getInstance(baos.toByteArray());
        float width = image.getWidth();
        float height = image.getHeight();
        is.close();
        baos.close();

        //get instance of PDF document by width and height
        Document document = new Document(new Rectangle(width,height));
        PdfWriter pdfWr = PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        //get all file and convert to img, and load in document
        for(Entry<Integer,File> eif : mif.entrySet())
        {
            //read into memory
            baos = new ByteArrayOutputStream(2048*3);
            is = new FileInputStream(eif.getValue());
            for(int len;(len=is.read())!=-1;)
                baos.write(len);
            baos.flush();

            //generate IMG object by byte
            image = Image.getInstance(baos.toByteArray());
            Image.getInstance(baos.toByteArray());
            image.setAbsolutePosition(0.0f, 0.0f);

            //add image to document
            document.add(image);
            document.newPage();
            baos.close();
        }

        document.close();
        pdfWr.close();

        return true;
    }

    public static void pdfToJpg(String source,String target,int x) throws Exception {
        // create RandomAccessFile
        // R: read mode
        RandomAccessFile rea = new RandomAccessFile(new File(source), "r");

        //read stream into memory and mapping  a PDF object
        FileChannel channel = rea.getChannel();
        ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY,0, channel.size());
        PDFFile pdfFile = new PDFFile(buf);
        PDFPage page = pdfFile.getPage(x);

        // get the width and height for the doc at the default zoom
        java.awt.Rectangle rect = new java.awt.Rectangle(0, 0, (int) page.getBBox()
                .getWidth(), (int) page.getBBox().getHeight());

        // generate the image
        java.awt.Image img = page.getImage(rect.width, rect.height, // width &
                rect, // clip rect
                null, // null for the ImageObserver
                true, // fill background with white
                true // block until drawing is done
        );

        BufferedImage tag = new BufferedImage(rect.width, rect.height,
                BufferedImage.TYPE_INT_RGB);

        tag.getGraphics().drawImage(img, 0, 0, rect.width, rect.height,
                null);
        FileOutputStream out = new FileOutputStream(target);
        ImageIO.write(tag, "jpg", out);

       // JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
       // encoder.encode(tag); // JPEG encoding
        tag.flush();
        buf.clear();
        channel.close();
        rea.close();
        out.close();
    }
    /**
     * @param source source PDF path
     * @param target target path
     * @param pageNum page number
     * @throws Exception
     */
    public static void pdfExtraction(String source,String target,int pageNum) throws Exception {
        //create PDF reader
        PdfReader pr = new PdfReader(source);
//        System.out.println("[pdfExtraction] this document "+pr.getNumberOfPages()+" page");

        //create document for the page: $param.pageNum
        Document doc = new Document(pr.getPageSize(pageNum));

        //copy the document
        PdfCopy copy = new PdfCopy(doc, new FileOutputStream(new File(target)));
        doc.open();
        doc.newPage();

        //get the first page and load in document
        PdfImportedPage page = copy.getImportedPage(pr,pageNum);
        copy.addPage(page);

        copy.close();
        doc.close();
        pr.close();
    }

    public static void jpgToPdf(File pdfFile,File imgFile) throws Exception {
        //convert document to img
        InputStream is = new FileInputStream(pdfFile);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for(int i;(i=is.read())!=-1;)
        {
            baos.write(i);
        }
        baos.flush();

        //get width and height of image
        Image img = Image.getInstance(baos.toByteArray());
        float width = img.getWidth();
        float height = img.getHeight();
        img.setAbsolutePosition(0.0F, 0.0F);//move skewing
//        System.out.println("[jpgToPdf] width = "+width+"\theight"+height);

        //covert img to pdf
        Document doc = new Document(new Rectangle(width,height));
        PdfWriter pw = PdfWriter.getInstance(doc,new FileOutputStream(imgFile));
        doc.open();
        doc.add(img);

//        System.out.println("jpgToPdf" + doc.newPage());
        pw.flush();
        baos.close();
        doc.close();
        pw.close();
        is.close();
    }

    /**
     * convert image to pdf
     * imgFilePath eg: imgFilePath="D:\\projectPath\\55555.jpg";
     * pdfFilePath eg: pdfFilePath="D:\\projectPath\\test.pdf";
     *
     * @param
     * @return
     * @throws IOException
     */


    public static boolean imgToPdf(String imgFilePath, String pdfFilePath) throws IOException {
        File file = new File(imgFilePath);
        if (file.exists()) {
            Document document = new Document();
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(pdfFilePath);
                PdfWriter.getInstance(document, fos);

                // add PDF additional info
                document.addAuthor("arui");
                document.addSubject("test pdf.");
                // set document size
                document.setPageSize(PageSize.A4);
                document.open();
                // input text
                //document.add(new Paragraph("JUST TEST ..."));
                // read image
                Image image = Image.getInstance(imgFilePath);
                float imageHeight = image.getScaledHeight();
                float imageWidth = image.getScaledWidth();
                int i = 0;
                while (imageHeight > 500 || imageWidth > 500) {
                    image.scalePercent(100 - i);
                    i++;
                    imageHeight = image.getScaledHeight();
                    imageWidth = image.getScaledWidth();
//                    System.out.println("[imgToPdf] imageHeight->" + imageHeight);
//                    System.out.println("[imgToPdf] imageWidth->" + imageWidth);
                }

                image.setAlignment(Image.ALIGN_CENTER);
                // image.setAbsolutePosition(0, 0);
                // image.scaleAbsolute(500, 400);
                // insert a image
                document.add(image);
            } catch (DocumentException de) {
//                System.out.println("[imgToPdf]" + de.getMessage());
            } catch (IOException ioe) {
//                System.out.println("[imgToPdf]" + ioe.getMessage());
            }
            document.close();
            fos.flush();
            fos.close();
            return true;
        } else {
            return false;
        }
    }

}
