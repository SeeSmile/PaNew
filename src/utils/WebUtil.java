package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;



/**
 * 
 */
public class WebUtil {
	
	public static SSLClient httpClient;
	
	public static String sendGET(String url) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        CloseableHttpResponse httpResponse = getClient().execute(httpGet);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent(), "utf-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response.toString();
    }
 
    public static String sendPOST(String url, List<NameValuePair> param) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        if(param != null) {
        	HttpEntity postParams = new UrlEncodedFormEntity(param);
            httpPost.setEntity(postParams);
        }
        CloseableHttpResponse httpResponse = getClient().execute(httpPost);
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent(), "utf-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response.toString();
    }
    
    public static void downImage(String url, String filename,
			String savePath) throws Exception {
    	HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        CloseableHttpResponse httpResponse = getClient().execute(httpGet);
        // 输入流  
 		InputStream is = httpResponse.getEntity().getContent();
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
    
    private static SSLClient getClient() throws Exception {
    	if(httpClient == null) {
    		httpClient = new SSLClient();
    	}
    	return httpClient;
    }
  
}