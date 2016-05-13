package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SFileUtil {
	
	public static void readFileLine(File file, final ReadListener listener) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
		final BufferedReader br = new BufferedReader(isr);   
			  new Thread(new Runnable() {
				
				@Override
				public void run() {
					String line;
					int index = 0;
					boolean isRun = true;
					while(isRun) {
						index++;
						try {
							line = br.readLine();
							if(line != null) {
								listener.onRead(index, line);
							} else {
								isRun = false;
							}
						} catch (IOException e) {
							listener.onFail();
						}
						
					}
					listener.onFinish();
				}
			}).start();
	}
	
	public interface ReadListener {
		public void onRead(int index, String text);
		public void onFinish();
		public void onFail();
	}
	
	private static String getRootFile() {
		return new File("").getAbsolutePath();
	}
	
	public static String getProFile(String name) {
		return new File(getRootFile(), name).getAbsolutePath();
	}
	
	public static String getDataFile(String name) {
		File file = new File(getProFile("data"), name);
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file.getAbsolutePath();
	}
	
	public static void lookChong(File file) throws IOException {
		final List<String> list = new ArrayList<>();
		final List<String> list_chong = new ArrayList<>();
		readFileLine(file, new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				if(trim(text).length() > 0) {
					if(!list.contains(text)) {
						list.add(text);
					} else {
						list_chong.add(text);
					}
				}
			}
			
			@Override
			public void onFinish() {
				System.out.println("总共:" + list.size());
				System.out.println("重复:" + list_chong.size());
			}
			
			@Override
			public void onFail() {
				
			}
		});
	}
	
	public static void lookChong(File file, final File newFile) throws IOException {
		final List<String> list = new ArrayList<>();
		final List<String> list_chong = new ArrayList<>();
		readFileLine(file, new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				if(trim(text).length() > 0) {
					if(!list.contains(text)) {
						list.add(text);
						FileUtil.writeText2File(newFile.getAbsolutePath(), text);
					} else {
						list_chong.add(text);
					}
				}
			}
			
			@Override
			public void onFinish() {
				System.out.println("去重后:" + list.size());
				System.out.println("重复:" + list_chong.size());
			}
			
			@Override
			public void onFail() {
				
			}
		});
	}
	
	public static File createDataFile(String path) {
		return new File(getDataFile(path));
	}
	
	public static String trim(String text) {
		if(text.indexOf("国") != -1) {
			return "";
		}
		if(text.indexOf("?") != -1) {
			return "";
		}
		String t;
		Pattern p = Pattern.compile("\\s*");
		Matcher m = p.matcher(text.trim());
		t = m.replaceAll("");
		
//		int pos = 0;
//		for(int i = 0; i < text.length(); i++) {
//			char c = text.charAt(i);
//			if(Character.isWhitespace(c)) {
//				pos = i;
//			}
//		}
//		t = text.substring(pos);
//		System.out.println("pos:" + pos + ", text:" + text + ", t:" + t);
		
		return t;
	}
}
