package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
	
}
