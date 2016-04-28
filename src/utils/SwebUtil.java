package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class SwebUtil {
	
	private List<HttpHost> hostlist;
	
	public SwebUtil(List<HttpHost> list) {
		this.hostlist = list;
	}
	
	public String doPortGet(String url) throws IOException {
		int index = new Random().nextInt(hostlist.size() - 1);
		return doPortGet(url, hostlist.get(index).getHostName(), hostlist.get(index).getPort());
	}
	
	private String doPortGet(String surl, String ip, int port) throws IOException {
		URL url = new URL(surl);
		URI uri = null;
		try {
			uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		 HttpHost proxy = new HttpHost(ip, port, "http");  
	     RequestConfig config = RequestConfig.custom().setProxy(proxy).build();  
	     HttpGet httpGet = new HttpGet(uri);  
//	     httpGet.setConfig(config);  
         httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        CloseableHttpResponse httpResponse = null;
		try{
			httpResponse = httpClient.execute(httpGet);
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
        httpClient.close();

        return result.toString();
	}
}
