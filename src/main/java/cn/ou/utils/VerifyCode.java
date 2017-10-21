package cn.ou.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 画验证码：利用java程序绘制验证码图片
 * @author Administrator
 *
 */
public class VerifyCode {
	/** 
	 * 图片的宽
	 */
	private static int width = 120;
	/**
	 * 图片的高
	 */
	private static int height = 30;
	/**
	 * 随机生成的字符“池”
	 */
	private static String codes = "23456789abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
	/**
	 * 图片字体
	 */
	private static String[] fontNames = { "微软雅黑", "黑体", "华文楷体", "幼圆", "宋体",
			"楷体" };
	/**
	 * 保存验证码文本
	 */
	private String valistr = "";
	
	
	/**
	 * 绘制验证码图片
	 */
	public void drawImage(OutputStream output){
		//1.创建一个图片缓冲区对象（代表一副图像）
		//并设置图片的宽高和类型
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//2.拿到画笔（得到绘制环境）
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		
		//3.开始画图
		/*画一个椭圆*/
		//g2.drawOval(0, 5, width, height);
		
		/*画一个矩形*/
		//g2.drawRect(5, 5, 20, 20);
		
		/*3.1.设置背景颜色*/
		g2.fillRect(0, 0, width, height);
		
		/*3.2.设置画笔颜色*/
		g2.setColor(Color.red);
		
		/*3.3.设置字体大小*/
		g2.setFont(new Font("微软雅黑", Font.BOLD, 22));
		
		/*3.4.画一个字符（串）*/
		for(int i=0;i<4;i++){
			/*3.4.1.设置字体颜色随机*/
			g2.setColor(new Color(getRandom(0,175),getRandom(0,175),getRandom(0,175)));
			
			/*3.4.2.设置字体随机*/
			g2.setFont(new Font(fontNames[getRandom(0, fontNames.length)], Font.BOLD, 22));
			
			/*3.4.3.将图片旋转指定的度数*/
			/*3.4.3.1.将随机获取的度数转成弧度*/
			double theta = getRandom(-45, +45) * Math.PI / 180;
			g2.rotate(theta, i * 30 + 7, height - 7);
			
			/*3.4.3.2.随机获取一个字符*/
			String code = codes.charAt(getRandom(0, codes.length())) + "";
			g2.drawString(code, i * 30 +7, height - 7);
			
			valistr = valistr + code;
			
			/*3.4.4.将图片再旋转回来*/
			g2.rotate(-theta, i * 30 + 7, height - 7);
			
			
		}
		
		/*3.5.画干扰线*/
		for(int i = 0; i < 6 ; i++){
			/*3.5.1.设置字体颜色随机*/
			g2.setColor(new Color(getRandom(0, 175),getRandom(0, 175),getRandom(0, 175)));
			
			/*3.5.2.随机画一条线*/
			g2.drawLine(getRandom(0, width), getRandom(0, height), getRandom(0, width), getRandom(0, height));
		}
		
		/*3.6.画一个边框*/
		/*3.6.1.设置边框的颜色*/
		g2.setColor(Color.GRAY);
		g2.drawRect(0, 0, width-1, height-1);
		
		//4.保存图片到指定位置（硬盘/发送给浏览器）
		try {
			ImageIO.write(bi, "JPEG", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//5.释放资源
		g2.dispose();
		
		
	}
	
	/**
	 * 返回验证码文本
	 * @return
	 */
	public String getCode(){
		return valistr;
	}
	
	
	/**
	 * 获取一个指定范围的随机数 0~10 3~10
	 * @param start
	 * @param end
	 * @return
	 */
	public int getRandom(int start,int end){
		Random random = new Random();
		return random.nextInt(end - start) + start;
	}
	
	public static void main(String[] args) throws Exception {
		VerifyCode vc = new VerifyCode();
		vc.drawImage(new FileOutputStream("./src/main/webapp/img/regist/vc.jpg"));
		
		System.out.println("执行完毕");
	}

}
