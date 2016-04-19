package utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameUtil {
	
	public static final int WINDOWS_WIDTH = 500;
	public static final int WINDOWS_HEIGHT = 300;
	
	public static void initWindows(JFrame jFrame) {
		jFrame.setSize(WINDOWS_WIDTH, WINDOWS_HEIGHT);
		int windowWidth = jFrame.getWidth(); //��ô��ڿ�
		int windowHeight = jFrame.getHeight(); //��ô��ڸ�
		Toolkit kit = Toolkit.getDefaultToolkit(); //���幤�߰�
		Dimension screenSize = kit.getScreenSize(); //��ȡ��Ļ�ĳߴ�
		int screenWidth = screenSize.width; //��ȡ��Ļ�Ŀ�
		int screenHeight = screenSize.height; //��ȡ��Ļ�ĸ�
		jFrame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2
				- windowHeight / 2);
	}
}
