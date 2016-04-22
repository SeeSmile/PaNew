package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {
	
	public static final String PATH_CODE = "c:\\images\\";
	
	/**
	 * 下載指定url的文件
	 * @param urlString url
	 * @param filename 文件的名字
	 * @param savePath 保存的path
	 * @throws Exception 
	 */
	public static void downloadFile(String urlString, String filename,
			String savePath) throws IOException {
		// 构造URL  
		URL url = new URL(urlString);
		// 打开连接  
		URLConnection con = url.openConnection();
		//设置请求超时为5s  
		con.setConnectTimeout(5 * 1000);
		// 输入流  
		InputStream is = con.getInputStream();
		// 1K的数据缓冲  
		byte[] bs = new byte[1024];
		// 读取到的数据长度  
		int len;
		// 输出的文件流  
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// 开始读取  
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接  
		os.close();
		is.close();
	}
	
	/**
	 * 创建当前要保存的二维码图片的文件
	 * @return 二维码图片文件
	 */
	public static File createCodeFile() {
		return new File(PATH_CODE + System.currentTimeMillis() + ".jpg");
	}
}
