package com.springboot.demo.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.EnumMap;
import java.util.Hashtable;

public class QRCodeUtils {

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int margin = 0;
    private static final int LogoPart = 4;


     // Generate QR code matrix

    public static BitMatrix setBitMatrix(String content, int width, int height) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); // set encoding to protect Chinese messy code
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // error level
        //hints.put(EncodeHintType.MARGIN, margin); // set the scope of white part
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitMatrix;
    }

    
     // output QR image to file

    public static void writeToFile(BitMatrix matrix, String format, OutputStream outStream, String logoPath, String footerPath) throws IOException {
        BufferedImage footerImage = ImageIO.read(new File(footerPath));
        BufferedImage image = toBufferedImage(matrix,footerImage.getHeight());
        // add LOGO into the center of image
        if (logoPath != null && !logoPath.equals("")) {
            image = addLogo(image, logoPath);
        }
        if(footerPath != null && !footerPath.equals("")){
            image = addFooterImage(image,footerPath);
        }
        ImageIO.write(image, format, outStream);
    }

     // generate QR image

    public static BufferedImage toBufferedImage(BitMatrix matrix, int footerHeight) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        //create image, contains header,body,footer
        //header height = footer height = @param(footerHeight)
        //body height = @param(matrix).getHeight()
        BufferedImage image = new BufferedImage(width, height+2 * footerHeight, BufferedImage.TYPE_3BYTE_BGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height+footerHeight; y++) {
                if(y < footerHeight){
                    image.setRGB(x, y, WHITE);
                }else{
                    image.setRGB(x, y, matrix.get(x, y-footerHeight) ? BLACK : WHITE);
                }
            }
        }
        return image;
    }

    // to add LOGO into center of the QR image

    public static BufferedImage addLogo(BufferedImage image, String logoPath) throws IOException {
        Graphics2D g = image.createGraphics();
        BufferedImage logoImage = ImageIO.read(new File(logoPath));
        // get the size of logo,can fit rectangle image,convert to square image based on the shorter side
        int width = image.getWidth() < image.getHeight() ? image.getWidth() / LogoPart : image.getHeight() / LogoPart;
        int height = width;
        // get the LOGO location
        int x = (image.getWidth() - width) / 2;
        int y = (image.getWidth() - height) / 2;
        // draw LOGO on QR image
        g.drawImage(logoImage, x, y, width, height, null);
        g.setStroke(new BasicStroke(2)); // set thickness of  brush
        g.setColor(Color.WHITE); // color of border
        g.drawRect(x, y, width, height); // set rectangle border
        logoImage.flush();
        g.dispose();
        return image;
    }

    public static BufferedImage addFooterImage(BufferedImage image, String footerPath) throws IOException {
        Graphics2D g = image.createGraphics();
        BufferedImage footerImage = ImageIO.read(new File(footerPath));
        int width = footerImage.getWidth();
        int height = footerImage.getHeight();
        // get the footer location
        int x = 0;
        int y = image.getWidth() + height;
        // draw footer on QR image
        g.drawImage(footerImage, x, y, width, height, null);
        g.setStroke(new BasicStroke(2)); // set thickness of  brush
        g.setColor(Color.BLACK); // color of border
        footerImage.flush();
        g.dispose();
        return image;
    }

    //add header of QR image

    public static void pressText(String pressText, String newImage, String targetImage, int fontStyle, Color color, int fontSize, int width, int height) {
        // start point:
        int startX = (width - (fontSize * pressText.length())) / 2;
        int startY = fontSize;
        try {
            File file = new File(targetImage);
            BufferedImage src = ImageIO.read(file);
            int imageW = src.getWidth(null);
            int imageH = src.getHeight(null);
            BufferedImage image = new BufferedImage(imageW, imageH, BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, imageW, imageH, null);
            g.setColor(color);
            g.setFont(new Font(null, fontStyle, fontSize));
            g.drawString(pressText, startX, startY);
            g.dispose();
            FileOutputStream out = new FileOutputStream(newImage);
            ImageIO.write(image, "png", out);
            out.close();
        } catch (IOException e) {
            System.out.println("[QRCode]" + e.getMessage());
        }
    }

    // combine two image to one

    public static void mergeImage(String combineType, String mainImgPath, String footerImgPath, String outputPath){
        try {
            File file1 = new File(mainImgPath);
            File file2 = new File(footerImgPath);
            BufferedImage img1 = ImageIO.read(file1);
            BufferedImage img2 = ImageIO.read(file2);
            int w1 = img1.getWidth();
            int h1 = img1.getHeight();
            int w2 = img2.getWidth();
            int h2 = img2.getHeight();
            // get RGB from image
            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // put all pixel into array by row
            int[] ImageArrayTwo = new int[w2 * h2];
            ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);
            // generate new image
            BufferedImage destImage = null;

            if("V".equals(combineType))
            {
                destImage = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
                destImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // set RGB for the top(left) half part
                destImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2); // set RGB for the bottom half part
            }
            else if("H".equals(combineType))
            {
                destImage = new BufferedImage(w1 + w2, h1, BufferedImage.TYPE_INT_RGB);
                destImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // set RGB for the top(left) half part
                destImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
            }

            FileOutputStream out  = new FileOutputStream(outputPath);
            ImageIO.write(destImage, "png", out);
            out.close();
        } catch (Exception e) {
            System.out.println("[QRCode]" + e.getMessage());
        }
    }



    // decode QR image
    
    public static String decodeQR(String filePath) {
//        if ("".equalsIgnoreCase(filePath) && filePath.length() == 0) {
//            System.out.println("[decodeQR] QR Image not found!");
//        }
//        System.out.println("[decode] filePath: " + filePath);
        String content = "";
        EnumMap<DecodeHintType, Object> hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8"); // set encoding
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
            BufferedImage image = ImageIO.read(inputStream);
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
            MultiFormatReader reader = new MultiFormatReader();
            Result result = reader.decode(binaryBitmap, hints);
            content = result.getText();
            inputStream.close();
        } catch (Exception e) {
            System.out.println("[QRCode]" + e.getMessage());
        }
        return content;
    }
}
