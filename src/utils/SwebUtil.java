package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.protocol.BasicHttpContext;

public class SwebUtil extends WebUtil{
	
	private static String[] IPS = {"", ""};
	private static String[] PORTS = {"", ""};
	private static int current_index = 0;
	
	public static String doPortGet(String url, String ip, int port) throws IOException {
		 HttpHost proxy = new HttpHost("115.229.252.68", 8888, "http");  
	     RequestConfig config = RequestConfig.custom().setProxy(proxy).build();  
	  
	        // «Î«Ûµÿ÷∑  
	     HttpGet httpGet = new HttpGet(url);  
	     httpGet.setConfig(config);  
	     BasicHttpContext context = new BasicHttpContext();
        httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        
        CloseableHttpResponse httpResponse = null;
		try {
			createClient();
			httpResponse = getClient().execute(httpGet, context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
}
