package com.cmazxiaoma.framework.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class QRCodeUtil {
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	
	/**
	 * 将二维码相关信息转换为BufferedImage
	 * @param matrix
	 * @return
	 */
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
	
	/**
	 * 将二维码相关信息转换为图片，写入图片文件
	 * @param matrix 二维码相关信息
	 * @param format 图片格式
	 * @param file 文件
	 * @throws IOException
	 */
	public static void writeToFile(BitMatrix matrix, String format, File file)
			throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format "
					+ format + " to " + file);
		}
	}
	
	/**
	 * 将二维码相关信息转换为图片，写入流
	 * @param matrix 二维码相关信息
	 * @param format 图片格式
	 * @param stream 流
	 * @throws IOException
	 */
	public static void writeToStream(BitMatrix matrix, String format,
			OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format "
					+ format);
		}
	}
	
	/**
	 * 生成二维码图片，写入输出流
	 * 
	 * @param info
	 *            文本信息
	 * @param width
	 *            宽度
	 * @param height
	 *            高度
	 */
	public static void generateQRCode(String info, int width, int height, OutputStream outStream) {
		if (StringUtils.isEmpty(info) || width <= 0 || height <= 0 || outStream == null) {
			return;
		}

		try {
			// 创建二维码属性集，控制二维码显示等特性
			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

			// 创建二维码对象
			MultiFormatWriter writer = new MultiFormatWriter();
			BitMatrix bitMatrix = writer.encode(info, BarcodeFormat.QR_CODE, width, height, hints);

			// 写入输出流
			writeToStream(bitMatrix, "png", outStream);
			outStream.flush();
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}