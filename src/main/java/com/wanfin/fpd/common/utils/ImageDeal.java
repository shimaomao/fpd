package com.wanfin.fpd.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;

import sun.misc.BASE64Encoder;

/**
 * 图像处理类.
 * 
 * @author nagsh
 * 
 */
public class ImageDeal extends Thread {
	private String pdf;
	private String png;
	private String xy;
	private String path;

	public ImageDeal(String pdf, String png) {
		super();
		this.pdf = pdf;
		this.png = png;
	}

	public ImageDeal(String xy, String path, String otherParam) {
		super();
		this.xy = xy;
		this.path = path;
	}

	/**
	 * 马赛克化.
	 * 
	 * @param size
	 *            马赛克尺寸，即每个矩形的长宽
	 * @return
	 * @return
	 * @throws Exception
	 */
	public static String mosaic(String xy, String path, Integer index) throws Exception {
		File file = new File(path);
		int border = 10;
		String xyArray[] = xy.split("\\|");
		BufferedImage bi = ImageIO.read(file);
		Graphics g2 = bi.getGraphics();
		for (int i = 0; i < xyArray.length; i++) {
			String x_y = xyArray[i];
			if (StringUtils.isEmpty(x_y))
				continue;
			Integer x = Integer.valueOf(x_y.split(", ")[0]), y = Integer.valueOf(x_y.split(", ")[1]);
			y = y - index * 500;
			if (y <= 0)
				y = 1;
			g2.setColor(new Color(237, 246, 255));
			g2.fillRect(x - border, y, 25, 25);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(bi, "jpg", outputStream);
		ImageIO.write(bi, "jpg", file);
		outputStream.flush();
		outputStream.close();
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(outputStream.toByteArray());
	}

	/**
	 * 马赛克化.
	 * 
	 * @param size
	 *            马赛克尺寸，即每个矩形的长宽
	 * @return
	 * @return
	 * @throws Exception
	 */
	private static Map<String, Object> fileMap = new HashMap<String, Object>();

	public static String mosaic(String xy, String path) throws Exception {
		File file = (File) fileMap.get("file-" + path);
		BufferedImage bi = (BufferedImage) fileMap.get("file-image-" + path);
		if (file == null) {
			file = new File(path);
			fileMap.put("file-" + path, file);
		}
		if (bi == null) {
			bi = ImageIO.read(file);
			fileMap.put("file-image-" + path, bi);
		}
		int border = 10;
		String xyArray[] = xy.split("\\|");
		Graphics g2 = bi.getGraphics();
		for (int i = 0; i < xyArray.length; i++) {
			String x_y = xyArray[i];
			if (StringUtils.isEmpty(x_y))
				continue;
			Integer x = Integer.valueOf(x_y.split(", ")[0]), y = Integer.valueOf(x_y.split(", ")[1]);
			if (y <= 0)
				y = 1;
			g2.setColor(new Color(237, 246, 255));
			g2.fillRect(x - border, y, 25, 25);
		}
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(bi, "jpg", outputStream);
		ImageIO.write(bi, "jpg", file);
		outputStream.flush();
		outputStream.close();
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(outputStream.toByteArray());
	}

	/**
	 * 纵向拼接一组（多张）图像
	 * 
	 * @param pics
	 *            将要拼接的图像数组
	 * @param type
	 *            写入图像类型
	 * @param dst_pic
	 *            写入图像路径
	 * @return
	 */
	public static boolean joinImage(BufferedImage[] images, String type, String dst_pic) {
		try {
			int len = images.length;
			if (len < 1) {
				System.out.println("pics len < 1");
				return false;
			}
			int[][] imageArrays = new int[len][];
			int dst_height = 0;
			int dst_width = images[0].getWidth();
			for (int i = 0; i < images.length; i++) {
				dst_width = dst_width > images[i].getWidth() ? dst_width : images[i].getWidth();
				dst_height += images[i].getHeight();
			}
			for (int i = 0; i < len; i++) {
				// System.out.println(i);
				int width = images[i].getWidth();
				int height = images[i].getHeight();
				imageArrays[i] = new int[dst_width * height];// 从图片中读取RGB
				imageArrays[i] = images[i].getRGB(0, 0, width, height, imageArrays[i], 0, dst_width);
				if (width < dst_width) {
					for (int j = 0; j < imageArrays[i].length; j++) {
						if (imageArrays[i][j] == 0) {
							imageArrays[i][j] = 0xffffff;
						}
					}
				}
			}
			if (dst_height < 1) {
				System.out.println("dst_height < 1");
				return false;
			}
			/*
			 * 生成新图片
			 */
			BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
			ImageNew.setRGB(255, 255, 255);
			int height_i = 0;
			for (int i = 0; i < images.length; i++) {
				ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(), imageArrays[i], 0, dst_width);
				height_i += images[i].getHeight();
			}
			File outFile = new File(dst_pic);
			ImageIO.write(ImageNew, type, outFile);// 写图片
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void pdf2png(String pdf, String png) throws Exception {
		float zoom = 1.8f;
		Document document = new Document();
		float rotation = 0f;
		document.setFile(pdf);
		int maxPages = document.getPageTree().getNumberOfPages();
		BufferedImage[] images = new BufferedImage[maxPages];
		for (int i = 0; i < maxPages; i++) {
			BufferedImage img = (BufferedImage) document.getPageImage(i, GraphicsRenderingHints.SCREEN,
					Page.BOUNDARY_CROPBOX, rotation, zoom);
			images[i] = img;
		}
		joinImage(images, "jpg", png);
	}

	public static void pdf2pngUseThread(String pdf, String png) throws PDFException, PDFSecurityException, IOException {
		Thread thread = new ImageDeal(pdf, png);
		thread.start();
	}

	public static void mosaicUseThread(String xy, String path) throws PDFException, PDFSecurityException, IOException {
		Thread thread = new ImageDeal(xy, path, null);
		thread.start();
	}

	@Override
	public void run() {
		try {
			if (this.xy != null) {
				ImageDeal.mosaic(xy, path);
			} else {
				ImageDeal.pdf2png(pdf, png);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.run();
	}

	public static void main(final String[] args) {
		// SwingUtilities.invokeLater(new Runnable() {
		// public void run() {
		// try {
		// ImageDeal.pdf2png("","");
		// } catch (Exception ex) {
		// ex.printStackTrace();
		// }
		// }
		// });
	}

}