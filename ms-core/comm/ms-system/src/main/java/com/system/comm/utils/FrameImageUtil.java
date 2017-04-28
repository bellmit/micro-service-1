package com.system.comm.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片操作工具类
 * @author yuejing
 * @date 2013-12-17 下午4:55:28
 * @version V1.0.0
 */
public class FrameImageUtil {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FrameImageUtil.class);

	/**
	 * 裁剪图片
	 * @param srcpath	源图片路径名称如:c:\1.jpg
	 * @param fileExt	图片后缀
	 * @param x			剪切点x坐标
	 * @param y
	 * @param width		剪切点宽度
	 * @param height
	 */
	public static byte[] cut(InputStream is, String fileExt, Integer x, Integer y, Integer width, Integer height) throws IOException {
		//InputStream is = null;
		ImageInputStream iis = null;
		try {
			//读取图片文件 is = new FileInputStream(srcpath);返回包含所有当前已注册 ImageReader 的 Iterator，这些 ImageReader 声称能够解码指定格式。参数：formatName - 包含非正式格式名称 . （例如 "jpeg" 或 "tiff"）等 。
			Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(fileExt);
			ImageReader reader = it.next();
			// 获取图片流
			iis = ImageIO.createImageInputStream(is);
			//<p>iis:读取源.true:只向前搜索 </p>.将它标记为 ‘只向前搜索’。此设置意味着包含在输入源中的图像将只按顺序读取，可能允许 reader 避免缓存包含与以前已经读取的图像关联的数据的那些输入部分。
			reader.setInput(iis, true);
			//<p>描述如何对流进行解码的类<p>.用于指定如何在输入时从 Java Image I/O 框架的上下文中的流转换一幅图像或一组图像。用于特定图像格式的插件 将从其 ImageReader 实现的getDefaultReadParam 方法中返回 ImageReadParam 的实例。
			ImageReadParam param = reader.getDefaultReadParam();
			//图片裁剪区域。Rectangle 指定了坐标空间中的一个区域，通过 Rectangle 对象的左上顶点的坐标（x，y）、宽度和高度可以定义这个区域。
			Rectangle rect = new Rectangle(x, y, width, height);
			// 提供一个 BufferedImage，将其用作解码像素数据的目标。
			param.setSourceRegion(rect);
			//使用所提供的 ImageReadParam 读取通过索引 imageIndex 指定的对象，并将 它作为一个完整的 BufferedImage 返回。
			BufferedImage bi = reader.read(0, param);

			ByteArrayOutputStream out = new ByteArrayOutputStream();  
			boolean flag = ImageIO.write(bi, fileExt, out);
			if(!flag) {
				throw new RuntimeException("上传文件异常");
			}
			byte[] b = out.toByteArray();
			return b;

			// 保存新图片
			//ImageIO.write(bi, "jpg", new File(subpath));
		} finally {
			if (is != null)
				is.close();
			if (iis != null)
				iis.close();
		}
	}

	/**
	 * 获取文件的宽度
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static int getWidth(InputStream inputStream) throws IOException {
		BufferedImage buffImg = ImageIO.read(inputStream);
		return buffImg.getWidth();
	}

	/***
	 * 调整图片大小(如果图片实际宽度小于调整后的宽度，则不调整)
	 * @param inputStream 	原图片的流
	 * @param targetPath	图片输出到目标路径
	 * @param fileExt	  	图片后缀
	 * @param width   		转换后图片宽度
	 * @param height  		转换后图片高度(为0代表只根据宽度缩放)
	 * @return
	 */
	public static boolean resize(InputStream inputStream, String targetPath, String fileExt, int width, int height) {
		/*try {
			BufferedImage buffImg = ImageIO.read(inputStream);//new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			BufferedImage newImage = null;
			if(buffImg.getWidth() > width) {
				//计算比例
				float scale = (float) width / buffImg.getWidth();
				if(height == 0) {
					height = (int) (buffImg.getHeight() * scale);
				}
				//Image img = ImageIO.read(inputStream);
				Image image = buffImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
				newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = newImage.createGraphics();
				//绘制缩小后的图
				g.drawImage(image, 0, 0, null);
				g.dispose();
				//Image img = buffImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				newImage.getGraphics().drawImage(
						img, 0, 0, null);
				newImage.getGraphics().drawImage(buffImg.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH),
						0, 0, width, height, null);
			} else {
				newImage = buffImg;
			}
			OutputStream out = new FileOutputStream(targetPath);
			boolean flag = ImageIO.write(newImage, "GIF", out);
			out.close();
			return flag;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		/*ByteArrayOutputStream out = new ByteArrayOutputStream();  
		boolean flag = ImageIO.write(newImage, fileExt, out);
		if(!flag) {
			throw new RuntimeException("上传文件异常");
		}
		byte[] b = out.toByteArray();
		return b;*/

		try {
			float scale = 1f;
			BufferedImage buffImg = ImageIO.read(inputStream);
			if(height == 0) {
				if(buffImg.getWidth() > width) {
					//计算比例
					scale = (float) width / buffImg.getWidth();
					//height = (int) (buffImg.getHeight() * scale);
				}/* else {
					height = buffImg.getHeight();
				}*/
			}
			/*
			 * 若图片横比200小，高比300小，不变
			 * 若图片横比200小，高比300大，高缩小到300，图片比例不变
			 * 若图片横比200大，高比300小，横缩小到200，图片比例不变
			 * 若图片横比200大，高比300大，图片按比例缩小，横为200或高为300
			 */
			Thumbnails.of(buffImg)
			//.size(width, height)
			.scale(scale)
			.toFile(targetPath);
			return true;
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * 给图片添加水印图片
	 * @param wmkPath 水印图片路径
	 * @param srcInputStream 源图片流
	 */
	public static byte[] wmkImg(String wmkPath, InputStream srcInputStream, String fileExt) {
		return wmkImg(wmkPath, srcInputStream, fileExt, 1f, null);
	}

	/**
	 * 给图片添加水印图片、可设置水印图片旋转角度
	 * @param wmkPath 水印图片路径
	 * @param srcInputStream 源图片流
	 * @param alpha	水印透明度
	 * @param degree 水印图片旋转角度
	 */
	public static byte[] wmkImg(String wmkPath, InputStream srcInputStream, String fileExt, Float alpha, Integer degree) {
		if(FrameStringUtil.isNotEqualIcArr(fileExt, "jpg", "jpeg")) {
			return null;
		}
		OutputStream os = null;
		try {
			Image srcImg = ImageIO.read(srcInputStream);
			int width = srcImg.getWidth(null);
			int height = srcImg.getHeight(null);

			Image srcWmk = ImageIO.read(new File(wmkPath));
			int widthWmk = srcWmk.getWidth(null);
			int heightWmk = srcWmk.getHeight(null);
			if(width < widthWmk) {
				return null;
			}

			BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

			// 1、得到画笔对象
			Graphics2D g = buffImg.createGraphics();

			// 2、设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			// 3、设置水印旋转
			if (null != degree) {
				g.rotate(Math.toRadians(degree),(double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
			}

			// 4、水印图片的路径 水印图片一般为gif或者png的，这样可设置透明度
			ImageIcon imgIcon = new ImageIcon(wmkPath);

			// 5、得到Image对象。
			Image img = imgIcon.getImage();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

			// 6、水印图片的位置
			g.drawImage(img, width - widthWmk, height - heightWmk, widthWmk, heightWmk, null);
			//g.drawImage(img, positionWidth, positionHeight, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
			// 7、释放资源
			g.dispose();

			// 8、生成图片
			ByteArrayOutputStream out = new ByteArrayOutputStream();  
			boolean flag = ImageIO.write(buffImg, fileExt, out);
			if(!flag) {
				throw new RuntimeException("上传文件异常");
			}
			return out.toByteArray();
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				if (os != null) os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("图像缩放--ImageDone");
		File file = new File("C:\\qhs/static/talk/2015/07/07/pnxd.jpg");
		InputStream in = new FileInputStream(file);

		FrameImageUtil.resize(in, "C:\\qhs/static/talk/2015/07/07/pnxd-small.jpg", "jpg", 500, 0);
		/*File uploadFile = new File("C:\\qhs/static/talk/2015/07/07/2.jpg");
		FileOutputStream fos = new FileOutputStream(uploadFile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		byte[] imageBytes = ImageUtil.resize(in, "jpg", 200, 0);
		bos.write(imageBytes);*/
		//new ImageUtil().zoom("C:\\qhs/static/talk/2015/07/07/1.jpg", "C:\\qhs/static/talk/2015/07/07/2.jpg", 0.5f);
	}
}