package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;

public class SwebUtil {
	
	
	private List<HttpHost> hostlist;
	private HttpClient mHttpClient;
	
	public SwebUtil(List<HttpHost> list) {
		try {
			this.hostlist = list;
			mHttpClient = new SSLClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String doPortGet(String url) throws IOException {
		int index = new Random().nextInt(hostlist.size() - 1);
		return doPortGet(url, hostlist.get(index).getHostName(), hostlist.get(index).getPort());
	}
	
	private String doPortGet(String url, String ip, int port) throws IOException {
		 HttpHost proxy = new HttpHost(ip, port, "http");  
	     RequestConfig config = RequestConfig.custom().setProxy(proxy).build();  
	     HttpGet httpGet = new HttpGet(url);  
	     httpGet.setConfig(config);  
         httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        CloseableHttpResponse httpResponse = null;
		try{
			httpResponse = (CloseableHttpResponse) mHttpClient.execute(httpGet);
		} catch (Exception e) {
			e.printStackTrace();
		}
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                httpResponse.getEntity().getContent(), "utf-8"));
        String inputLine;
        StringBuffer result = new StringBuffer();
        while ((inputLine = reader.readLine()) != null) {
            result.append(inputLine);
        }
        reader.close();
        return result.toString();
	}
}
