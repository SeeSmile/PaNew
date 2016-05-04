package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
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
	
	
	public String doPortGet(String surl) throws IOException, URISyntaxException {
		RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
	     HttpGet httpGet = null;
		try {
			httpGet = new HttpGet(surl);
		} catch (Exception e) {
			throw new URISyntaxException("", "");
		}  
         httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        CloseableHttpResponse httpResponse = null;
		httpResponse = httpClient.execute(httpGet);
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
