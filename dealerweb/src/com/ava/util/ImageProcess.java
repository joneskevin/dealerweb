package com.ava.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import javax.imageio.ImageIO;

import com.ava.resource.GlobalConfig;

public class ImageProcess {
	private static final String SMALL_IMAGE_SUFFIX = "_small";
	private static final String STANDARD_IMAGE_SUFFIX = "_standard";

	public static String buildSmallJpg (String oldpic) {
		return buildSmallJpg(oldpic, 200);
	}

	/**
	 * 根据原来的大图路径和缩略图存放路径以及最大尺寸生成标准缩略图，并返回虚拟相对路径的缩略图路径文件名，用于数据库存放等
	 * @ 注意：此方法一定会处理图片，使得图片符合尺寸要求
	 * @ oldpic = /usr/local/tomcat/webapps/demosite/upload/200605/test.jpg
	 * @ newsize = 80
	 * @retuen:	/upload/200605/test_small.jpg
	 */
	public static String buildSmallJpg (String oldpic, int newsize) {
		return buildSmallJpg(oldpic, null, newsize);
	}

	/**
	 * 根据原来的大图路径和缩略图存放路径以及最大尺寸生成标准缩略图，并返回虚拟相对路径的缩略图路径文件名，用于数据库存放等
	 * @ 注意：此方法一定会处理图片，使得图片符合尺寸要求
	 * @ oldpic = /usr/local/tomcat/webapps/demosite/upload/200605/test.jpg
	 * @ newpic = /usr/local/tomcat/webapps/demosite/upload/200605/test_small.jpg
	 * @ newsize = 100
	 * @retuen:	/upload/200605/test_small.jpg
	 */
	public static String buildSmallJpg (String oldpic, String newpic, int newsize) {
        try {
        	if (oldpic==null || "".equals(oldpic)){
        		return "";
        	}
        	if (newpic==null || "".equals(newpic)){
        		newpic = getSmallImage(oldpic);
        	}
			//建立输出文件的目录
			FileUtil.mkDir(FileUtil.getPath(newpic));
			
            File fi = new File(oldpic); //大图文件
            File fo = new File(newpic); //将要转换出的小图文件

            BufferedImage bis = ImageIO.read(fi);

            /**	原始图片宽度	*/
            int w = bis.getWidth();
            /**	原始图片高度	*/
            int h = bis.getHeight();
            //double scale = (double)w/h;

            int nw = newsize;
            int nh = (nw * h) / w;
            if(nh>newsize) {
                nh = newsize;
                nw = (nh * w) / h;
            }

            BufferedImage bid = new BufferedImage(nw, nh, BufferedImage.TYPE_3BYTE_BGR);
            //============== 开始绘制缩略后的图片 ==============
            /**
            double sx = (double)nw / w;
            double sy = (double)nh / h;
            AffineTransform transform = new AffineTransform();
            transform.setToScale(sx, sx);
            AffineTransformOp ato = new AffineTransformOp(transform, null);
            ato.filter(bis,bid);
            ImageIO.write(bid, "JPEG", fo);
            */
        	//============== 开始绘制缩略后的图片（GIF不会失真） ==============
			Image image = bis.getScaledInstance(nw, nh, Image.SCALE_DEFAULT);
			Graphics g = bid.getGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
            ImageIO.write(bid, "JPEG", fo);
            
        	return getVirtualFilePathName(newpic);
		
        } catch(Exception e) {
        	System.out.println("com.util.ImageProcess.buildSmallJpg() process failed........");
            e.printStackTrace();
            return "";
        }
	}
	
	public static String buildStandardJpg (String oldpic) {
		return buildStandardJpg(oldpic, 400);
	}

	/**
	 * 根据原来的大图路径和缩略图存放路径以及最大尺寸生成缩略图，并返回虚拟相对路径的缩略图路径文件名，用于数据库存放等
	 * @ 注意：此方法不一定会缩略图片，只有在原始图片大于尺寸时才处理
	 * @ oldpic = /usr/local/tomcat/webapps/demosite/upload/200605/test.jpg
	 * @ newsize = 400
	 * @retuen:	/upload/200605/test_standard.jpg
	 */
	public static String buildStandardJpg (String oldpic, int newsize) {
		return buildStandardJpg(oldpic, null, newsize);
	}

	/**
	 * 根据原来的大图路径和缩略图存放路径以及最大尺寸生成缩略图，并返回虚拟相对路径的缩略图路径文件名，用于数据库存放等
	 * @ 注意：此方法不一定会缩略图片，只有在原始图片大于尺寸时才处理
	 * @ oldpic = /usr/local/tomcat/webapps/demosite/upload/200605/test.jpg
	 * @ newpic = /usr/local/tomcat/webapps/demosite/upload/200605/test_standard.jpg
	 * @ newsize = 100
	 * @retuen:	/upload/200605/test_standard.jpg
	 */
	public static String buildStandardJpg (String oldpic, String newpic, int newsize) {
        File fi = new File(oldpic); //大图文件
        try{
        	if (oldpic==null || "".equals(oldpic)){
        		return "";
        	}
        	if (newpic==null || "".equals(newpic)){
        		newpic = getStandardImage(oldpic);
        	}
			//建立输出文件的目录
			FileUtil.mkDir(FileUtil.getPath(newpic));
			
        	BufferedImage bis = ImageIO.read(fi);
        	/**	原始图片宽度	*/
	        int w = bis.getWidth();
	        /**	原始图片高度	*/
	        int h = bis.getHeight();
	        if (w < newsize && h < newsize){
	        	//如果尺寸长宽都比需要修改的小，则不需要缩小化图片处理，复制即可
	        	FileUtil.copyFile(oldpic, newpic);
	        	return getVirtualFilePathName(newpic);
	        }else{
	        	//如果需要图片处理，则转到buildSmallJpg()方法
	        	return buildSmallJpg(oldpic, newpic, newsize);
	        }
        }catch(Exception e){
        	System.out.println("com.util.ImageProcess.buildStandardJpg() process failed........");
            e.printStackTrace();
        	return "";        	
        }
    }
	
	/**
	 * 根据原来的图片绝对路径得到方便数据库存放的文件相对路径
	 * oldpic = /usr/local/tomcat/webapps/demosite/upload/200605/test.gif
	 * retuen:	/upload/200605/test.gif
	 */
	private static String getVirtualFilePathName (String physicalFilePathName) {
		String virtualFilePathName;
		if (GlobalConfig.getDefaultAppPath() != null){
			virtualFilePathName = physicalFilePathName.substring(GlobalConfig.getDefaultAppPath().length());
		}else{
			virtualFilePathName = physicalFilePathName;
		}
		return virtualFilePathName;
	}
	
	/**
	 * 根据原来的大图路径得出缩略图的存放路径及文件名
	 * oldpic = /usr/local/tomcat/webapps/demosite/test.gif
	 * retuen:	/usr/local/tomcat/webapps/demosite/test_small.gif
	 */
	public static String getSmallImage (String oldpic) {
		String result = null;
		String filepath = FileUtil.getPath(oldpic);
		String filename = FileUtil.getFileName(oldpic);
		int pos = filename.lastIndexOf(".");
		if (pos > -1){
			result = filepath + filename.substring(0,pos) + SMALL_IMAGE_SUFFIX + filename.substring(pos);
		}
		return result;
	}
	
	/**
	 * 根据原来的大图路径得出标准图的存放路径及文件名
	 * oldpic = /usr/local/tomcat/webapps/demosite/test.gif
	 * retuen:	/usr/local/tomcat/webapps/demosite/test_standard.gif
	 */
	public static String getStandardImage (String oldpic) {
		String result = null;
		String filepath = FileUtil.getPath(oldpic);
		String filename = FileUtil.getFileName(oldpic);
		int pos = filename.lastIndexOf(".");
		if (pos > -1){
			result = filepath + filename.substring(0,pos) + STANDARD_IMAGE_SUFFIX + filename.substring(pos);
		}
		return result;
	}
	
	
	/**
	 * 图片切割
	 * 
	 * @param srcImageFile
	 *            旧图片地址（绝对地址）
	 * @param newImageFile
	 *            新图片地址（绝对地址）
	 * @param w
	 *            切割宽度
	 * @param h
	 *            切割高度
	 * @param x1
	 *            开始x结点（left）
	 * @param y1
	 *            开始y结点（top）
	 * @param sw
	 *            图片宽度
	 * @param sh
	 *            图片高度
	 */
	public static void cut(String srcImageFile, String newImageFile, int w, int h, int x1, int y1, int sw, int sh) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			
			//if (sw >= w || sh >= h) {
			if ( true ) {
				Image image = bi.getScaledInstance(sw, sh, Image.SCALE_DEFAULT);
				// 剪切起始坐标点
				int x = x1;
				int y = y1;
				int destWidth = w; // 切片宽度
				int destHeight = h; // 切片高度
				// 图片比例
				double pw = sw;
				double ph = sh;
				double m = (double) sw / pw;
				double n = (double) sh / ph;

				int wth = (int) (destWidth * m);
				int hth = (int) (destHeight * n);
				int xx = (int) (x * m);
				int yy = (int) (y * n);

				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				cropFilter = new CropImageFilter(xx, yy, wth, hth);
				img = Toolkit.getDefaultToolkit().createImage(
						new FilteredImageSource(image.getSource(), cropFilter));

				BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.setColor(Color.white);	//图片底色为白色
				g.fillRect(0, 0, w, h);		//先画一个白底矩形 
				g.drawImage(img, 0, 0, null); // 绘制缩小后的图
				g.dispose();

				//建立输出文件的目录
				FileUtil.mkDir(FileUtil.getPath(newImageFile));
				// 输出为文件
				File fo = new File(newImageFile);
				ImageIO.write(tag, "JPEG", fo);

			}else{
				//如果要切割的宽度或高度比缩放后的图片原本大小还要大，则直接根据图片缩放后的尺寸缩放图片，不做切割处理
				int newsize = sw > sh ? sw : sh ;
				buildSmallJpg(srcImageFile, newImageFile, newsize);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	//读取远程url图片,得到宽高   
    public static String downloadImage(String imgurl){   
    	String filepath=null;
		String filename=null;
		String virtualPath="";
		String physicalPath="";

		String fileExt=FileUtil.getExtName(imgurl);
		Date now=new Date();
		filename=now.getTime()+"."+fileExt;		
		filepath = "/upload/" + DateTime.getYear() + "/" + DateTime.getMonth() + "/" + DateTime.getDay() + "/";
		
		String appPath = "D:/Now-Projects/infox/03_Source/WebContent";
		FileUtil.mkDir(appPath + filepath );
		virtualPath = filepath+filename;	
		physicalPath = appPath + virtualPath;	  
        try {   
            //实例化url   
            URL url = new URL(imgurl);   
            //载入图片到输入流   
            java.io.BufferedInputStream bis = new BufferedInputStream(url.openStream());   
            //实例化存储字节数组   
            byte[] bytes = new byte[1024*8];   
            //设置写入路径以及图片名称   
            OutputStream bos = new FileOutputStream(new File(physicalPath));   
            int len;   
            while ((len = bis.read(bytes, 0, 1024*8)) > 0) {   
                bos.write(bytes, 0, len);   
            }   
            bis.close();   
            bos.flush();   
            bos.close();   
        } catch (Exception e) {   
        }     
       return virtualPath;   
  
    } 

	
	public static void main (String argv[]) {
		buildSmallJpg("D:/temp/B4FCDC00BF7FC641.jpg", "D:/temp/test1.jpg", 200);
		buildSmallJpg("D:/temp/B43B1D425224FCF5.jpg", "D:/temp/test2.jpg", 200);
		buildSmallJpg("D:/temp/baseLogo.jpg", "D:/temp/baseLogo_test.gif", 200);
		System.out.println("process successful........");
	}

}
