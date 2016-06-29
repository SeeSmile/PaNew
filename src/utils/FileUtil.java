package utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {
	
	public static final String PATH_CODE = "c:\\images\\";
	
	/**
	 * ���dָ��url���ļ�
	 * @param urlString url
	 * @param filename �ļ�������
	 * @param savePath �����path
	 * @throws Exception 
	 */
	public static void downloadFile(String urlString, String filename,
			String savePath) throws IOException {
		// ����URL  
		URL url = new URL(urlString);
		// ������  
		URLConnection con = url.openConnection();
		//��������ʱΪ5s  
		con.setConnectTimeout(5 * 1000);
		// ������  
		InputStream is = con.getInputStream();
		// 1K����ݻ���  
		byte[] bs = new byte[1024];
		// ��ȡ������ݳ���  
		int len;
		// ������ļ���  
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// ��ʼ��ȡ  
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// ��ϣ��ر���������  
		os.close();
		is.close();
	}
	
	/**
	 * ������ǰҪ����Ķ�ά��ͼƬ���ļ�
	 * @return ��ά��ͼƬ�ļ�
	 */
	public static File createCodeFile() {
		return new File(PATH_CODE + System.currentTimeMillis() + ".jpg");
	}
	
	public static void writeText2File(String fileName, String content) {   
        FileWriter writer = null;  
        try {     
           
            writer = new FileWriter(fileName, true);     
            writer.write("\r\n");
            writer.write(content);       
        } catch (IOException e) {     
            e.printStackTrace();     
        } finally {     
            try {     
                if(writer != null){  
                    writer.close();     
                }  
            } catch (IOException e) {     
                e.printStackTrace();     
            }     
        }   
    }    
}
