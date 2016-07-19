package com.ava.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**验证码管理类*/
public class AuthenCodeImage extends HttpServlet {
	
	//设置保存的session对象，用户用户提交时进行匹配的操作。
	public static final String SESSION_AUTHEN_CODE = "checkimage"; 
	
	private int imgWidth = 200;

	private int imgHeight = 80;

	private int codeCount = 4;

	private int x = 0;

	private int codeY;

	private int fontHeight = 50;

	private String fontStyle = "Arial";

	/**
	 * 初始化配置参数
	 */
	public void init() throws ServletException {
		// 从web.xml中获取初始信息
		// 宽度
		String strWidth = this.getInitParameter("imgWidth");
		// 高度
		String strHeight = this.getInitParameter("imgHeight");
		// 字符个数
		String strCodeCount = this.getInitParameter("codeCount");

		fontStyle = this.getInitParameter("fontStyle");

		// 将配置的信息转换成数值
		try {
			if (strWidth != null && strWidth.length() != 0) {
				imgWidth = Integer.parseInt(strWidth);
			}
			if (strHeight != null && strHeight.length() != 0) {
				imgHeight = Integer.parseInt(strHeight);
			}
			if (strCodeCount != null && strCodeCount.length() != 0) {
				codeCount = Integer.parseInt(strCodeCount);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		x = imgWidth / (codeCount + 1);
		fontHeight = imgHeight - 2;
		codeY = imgHeight - 12;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		HttpSession session = request.getSession();

		// 在内存中创建图象
		BufferedImage image = new BufferedImage(imgWidth, imgHeight,
				BufferedImage.TYPE_INT_RGB);

		// 获取图形上下文
		Graphics2D g = image.createGraphics();

		// 生成随机类
		Random random = new Random();

		// 设定背景色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, imgWidth, imgHeight);

		// 设定字体
		g.setFont(new Font(fontStyle, Font.PLAIN + Font.ITALIC, fontHeight));

		// 画边框
		g.setColor(new Color(55, 55, 12));
		g.drawRect(0, 0, imgWidth - 1, imgHeight - 1);

		// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 160; i++) {
			int x = random.nextInt(imgWidth);
			int y = random.nextInt(imgHeight);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}

		// 取随机产生的认证码(4位数字)
		String sRand = "";
		int red = 0, green = 0, blue = 0;
		for (int i = 0; i < codeCount; i++) {
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			int wordType = random.nextInt(3);
			char retWord = 0;
			switch (wordType) {
			case 0:
				retWord = this.getSingleNumberChar();
				break;
			case 1:
				retWord = this.getLowerOrUpperChar(0);
				break;
			case 2:
				retWord = this.getLowerOrUpperChar(1);
				break;
			}
			sRand += String.valueOf(retWord);
			g.setColor(new Color(red, green, blue));
			g.drawString(String.valueOf(retWord), (i) * x, codeY);

		}
		// 将认证码存入SESSION
		request.getSession().setAttribute(SESSION_AUTHEN_CODE, sRand.toLowerCase());
		// 图象生效
		g.dispose();
		ServletOutputStream responseOutputStream = response.getOutputStream();
		// 输出图象到页面
		ImageIO.write(image, "JPEG", responseOutputStream);

		// 以下关闭输入流！
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private char getSingleNumberChar() {
		Random random = new Random();
		int numberResult = random.nextInt(10);
		int ret = numberResult + 48;
		return (char) ret;
	}

	private char getLowerOrUpperChar(int upper) {
		Random random = new Random();
		int numberResult = random.nextInt(26);
		int ret = 0;
		if (upper == 0) {// 小写
			ret = numberResult + 97;
		} else if (upper == 1) {// 大写
			ret = numberResult + 65;
		}
		return (char) ret;
	}
}