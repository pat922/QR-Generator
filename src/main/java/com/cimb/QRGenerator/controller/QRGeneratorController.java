package com.cimb.QRGenerator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cimb.QRGenerator.vo.QRStringVO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.pdf.codec.Base64;

import java.nio.charset.StandardCharsets;

/**
 * @author Foo Yong Li & Tan Soo Ding
 * @dateTime 20210424
 */

@RestController
@RequestMapping("qr")
public class QRGeneratorController {

	private static final Logger LOGGER = LoggerFactory.getLogger(QRGeneratorController.class);
	@Value("${logopath}")
	String logoPath;

	@PostMapping("/test")
	public String generator() {
		return java.time.LocalDateTime.now().toString();
	}

	private BufferedImage getOverly(String logoPath) throws IOException {
		return ImageIO.read(new File(logoPath));
	}

	private MatrixToImageConfig getMatrixConfig() {
		// ARGB Colors
		// Check Colors ENUM
		return new MatrixToImageConfig(QRGeneratorController.Colors.WHITE.getArgb(), QRGeneratorController.Colors.ORANGE.getArgb());
	}

	public enum Colors {

		BLUE(0xFF40BAD0), RED(0xFFE91C43), PURPLE(0xFF8A4F9E), ORANGE(0xFFF4B13D), WHITE(0xFFFFFFFF), BLACK(0xFF000000);

		private final int argb;

		Colors(final int argb) {
			this.argb = argb;
		}

		public int getArgb() {
			return argb;
		}
	}

	@PostMapping("/qrgenerator")
	public String generateQR(@RequestBody QRStringVO qrStringVO) throws IOException {
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, 0);

		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
			// Create a qr code with the url as content and a size of 250x250 px
			int width = 400, height = 400;

			LOGGER.info(qrStringVO.toString());
//if-else
			StringBuffer contentBuffer = new StringBuffer(qrStringVO.getPayloadFormatIndicator())
					.append(qrStringVO.getInitMethod()).append(qrStringVO.getMerchantAccInfoId())
					.append(qrStringVO.getMerchantAccInfoStrLength()).append(qrStringVO.getGlobalUniqueId())
					.append(qrStringVO.getPayNowIdTypePrefix()).append(qrStringVO.getPayNowIdType())
					.append(qrStringVO.getProxyValueId()).append(qrStringVO.getProxyValueLength())
					.append(qrStringVO.getProxyValue()).append(qrStringVO.getEditableAmountFlag());
					
					
		
			if (!(qrStringVO.getQrValidTill().isEmpty())) {
				contentBuffer.append(qrStringVO.getQrValidTill());
			}
			if (!(qrStringVO.getEndToEndReference().isEmpty())) {
			contentBuffer.append(qrStringVO.getEndToEndReference());
			}
			contentBuffer.append(qrStringVO.getMerchantCategory()).append(qrStringVO.getTransactionCurrency());
			
			if (!(qrStringVO.getAmountQr().isEmpty())) {
			contentBuffer.append(qrStringVO.getAmountQr());
			}
			
			contentBuffer.append(qrStringVO.getCountryCode()).append(qrStringVO.getMerchantNameStr()).append(qrStringVO.getMerchantCity());
			
			if(!(qrStringVO.getQrBillNum().isEmpty())) {
				contentBuffer.append(qrStringVO.getAdditionalDataId());
				contentBuffer.append(qrStringVO.getAdditionalDataLength());
				contentBuffer.append(qrStringVO.getQrBillNum());
			}
			contentBuffer.append(qrStringVO.getCrcChecksumId()).append(qrStringVO.getCrcChecksumLength());

			String generateChecksum = generateChecksum(contentBuffer.toString());
			
			String finalContent = contentBuffer.append(generateChecksum).toString();

			bitMatrix = writer.encode(finalContent, BarcodeFormat.QR_CODE, width, height, hints);

			int PURPLE = -8643976;
			MatrixToImageConfig config = new MatrixToImageConfig(PURPLE, MatrixToImageConfig.WHITE);

			// Load QR image
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

			// Load logo image
			java.net.URL url = this.getClass().getResource(logoPath);
			BufferedImage logoImage = ImageIO.read(url.openStream());

			// Calculate the delta height and width between QR code and logo
			int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
			int deltaWidth = qrImage.getWidth() - logoImage.getWidth();

			// Initialize combined image
			BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(),
			BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) combined.getGraphics();

			// Write QR code to new image at position 0/0
			g.drawImage(qrImage, 0, 0, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

			// Write logo into combine image at position (deltaWidth / 2) and
			// (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
			// the same space for the logo to be centered
			g.drawImage(logoImage, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);

			// Write combined image as PNG to OutputStream
			// ImageIO.write(combined, "png", baos);
			
			///////
			ByteArrayOutputStream tempOs = new ByteArrayOutputStream();
			ImageIO.write(combined, "png", tempOs);
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(tempOs.toByteArray()));
			//ImageIO.write(combined, "png", new File(generatedqr));
			//BufferedImage img = ImageIO.read(new File(generatedqr));
			ImageIO.write(img, "png", baos);
			baos.flush();

			String base64String = Base64.encodeBytes(baos.toByteArray());
			tempOs.close();
			LOGGER.info(base64String);
			LOGGER.info("completed ....");
			
			return base64String;
		} catch (Exception e) {
			LOGGER.error("Error: " + e, e);
			return ("error" + e);
		}
	}

	protected String generateChecksum(String content) {
		int checksum = 0xffff;
		int polynomial = 0x1021;

		byte[] data = content.getBytes(StandardCharsets.UTF_8);
		for (byte b : data) {
			for (int i = 0; i < 8; i++) {
				boolean bit = ((b >> (7 - i) & 1) == 1);
				boolean c15 = ((checksum >> 15 & 1) == 1);
				checksum <<= 1;
				if (c15 ^ bit) {
					checksum ^= polynomial;
				}
			}
		}
		checksum &= 0xffff;
		return String.format("%04X", checksum);
	}
}