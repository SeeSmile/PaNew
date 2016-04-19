package utils;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class FrameUtil {
	
	public static final int WINDOWS_WIDTH = 500;
	public static final int WINDOWS_HEIGHT = 300;
	
	public static void initWindows(JFrame jFrame) {
		jFrame.setSize(WINDOWS_WIDTH, WINDOWS_HEIGHT);
		int windowWidth = jFrame.getWidth(); //获得窗口宽
		int windowHeight = jFrame.getHeight(); //获得窗口高
		Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
		Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
		int screenWidth = screenSize.width; //获取屏幕的宽
		int screenHeight = screenSize.height; //获取屏幕的高
		jFrame.setLocation(screenWidth / 2 - windowWidth / 2, screenHeight / 2
				- windowHeight / 2);
	}
}
