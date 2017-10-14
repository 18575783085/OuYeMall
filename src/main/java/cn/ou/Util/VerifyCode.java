package cn.ou.Util;

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
 * ����֤�룺����java���������֤��ͼƬ
 * @author Administrator
 *
 */
public class VerifyCode {
	/**
	 * ͼƬ�Ŀ�
	 */
	private static int width = 120;
	/**
	 * ͼƬ�ĸ�
	 */
	private static int height = 30;
	/**
	 * ������ɵ��ַ����ء�
	 */
	private static String codes = "23456789abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ";
	/**
	 * ͼƬ����
	 */
	private static String[] fontNames = { "΢���ź�", "����", "���Ŀ���", "��Բ", "����",
			"����" };
	/**
	 * ������֤���ı�
	 */
	private String valistr = "";
	
	
	/**
	 * ������֤��ͼƬ
	 */
	public void drawImage(OutputStream output){
		//1.����һ��ͼƬ���������󣨴���һ��ͼ��
		//������ͼƬ�Ŀ�ߺ�����
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//2.�õ����ʣ��õ����ƻ�����
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		
		//3.��ʼ��ͼ
		/*��һ����Բ*/
		//g2.drawOval(0, 5, width, height);
		
		/*��һ������*/
		//g2.drawRect(5, 5, 20, 20);
		
		/*3.1.���ñ�����ɫ*/
		g2.fillRect(0, 0, width, height);
		
		/*3.2.���û�����ɫ*/
		g2.setColor(Color.red);
		
		/*3.3.���������С*/
		g2.setFont(new Font("΢���ź�", Font.BOLD, 22));
		
		/*3.4.��һ���ַ�������*/
		for(int i=0;i<4;i++){
			/*3.4.1.����������ɫ���*/
			g2.setColor(new Color(getRandom(0,175),getRandom(0,175),getRandom(0,175)));
			
			/*3.4.2.�����������*/
			g2.setFont(new Font(fontNames[getRandom(0, fontNames.length)], Font.BOLD, 22));
			
			/*3.4.3.��ͼƬ��תָ���Ķ���*/
			/*3.4.3.1.�������ȡ�Ķ���ת�ɻ���*/
			double theta = getRandom(-45, +45) * Math.PI / 180;
			g2.rotate(theta, i * 30 + 7, height - 7);
			
			/*3.4.3.2.�����ȡһ���ַ�*/
			String code = codes.charAt(getRandom(0, codes.length())) + "";
			g2.drawString(code, i * 30 +7, height - 7);
			
			valistr = valistr + code;
			
			/*3.4.4.��ͼƬ����ת����*/
			g2.rotate(-theta, i * 30 + 7, height - 7);
			
			
		}
		
		/*3.5.��������*/
		for(int i = 0; i < 6 ; i++){
			/*3.5.1.����������ɫ���*/
			g2.setColor(new Color(getRandom(0, 175),getRandom(0, 175),getRandom(0, 175)));
			
			/*3.5.2.�����һ����*/
			g2.drawLine(getRandom(0, width), getRandom(0, height), getRandom(0, width), getRandom(0, height));
		}
		
		/*3.6.��һ���߿�*/
		/*3.6.1.���ñ߿����ɫ*/
		g2.setColor(Color.GRAY);
		g2.drawRect(0, 0, width-1, height-1);
		
		//4.����ͼƬ��ָ��λ�ã�Ӳ��/���͸��������
		try {
			ImageIO.write(bi, "JPEG", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//5.�ͷ���Դ
		g2.dispose();
		
		
	}
	
	/**
	 * ������֤���ı�
	 * @return
	 */
	public String getCode(){
		return valistr;
	}
	
	
	/**
	 * ��ȡһ��ָ����Χ������� 0~10 3~10
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
		
		System.out.println("ִ�����");
	}

}
