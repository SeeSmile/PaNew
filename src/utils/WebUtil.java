package utils;

import helper.WXhelper;

import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.xml.ws.spi.http.HttpContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.mime.Header;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.jsoup.Jsoup;




/**
 * 
 */
public class WebUtil {
	
	public static SSLClient httpClient;
	public static String cookie = "";
	public static List<String> LIST_SUID = new ArrayList<>();
	public static List<String> LIST_SUIR = new ArrayList<>();
	
	public static String sendGET(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
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
//        httpResponse.getEntity().getContent()
//        HttpHost targetHost = (HttpHost)context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
        //获取实际的请求对象的URI,即重定向之后的"/blog/admin/login.jsp"
//        HttpUriRequest realRequest = (HttpUriRequest)context.getAttribute(ExecutionContext.HTTP_REQUEST);
//        System.out.println("主机地址:" + targetHost);
//        System.out.println("URI信息:" + realRequest.getURI());
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
    
    protected static SSLClient getClient() {
    	if(httpClient == null) {
    		try {
				httpClient = new SSLClient();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return httpClient;
    }
    
    public static String getHttpContent(String url) {
        return getHttpContent(url, "UTF-8");
    }

    public static String getHttpContent(String url, String charSet) {
        HttpURLConnection connection = null;
        String content = "";
        try {
            URL address_url = new URL(url);
            connection = (HttpURLConnection) address_url.openConnection();
//            connection.setRequestMethod("GET");
            //设置访问超时时间及读取网页流的超市时间,毫秒值
            System.setProperty("sun.net.client.defaultConnectTimeout","30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");

            //after JDK 1.5
//            connection.setConnectTimeout(10000);
//            connection.setReadTimeout(10000);
            //得到访问页面的返回值
            int response_code = connection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(in,charSet);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, charSet));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content+=line;
                }
                return content;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection !=null){
                connection.disconnect();
            }
        }
        return "";
    }
    
    public static String getRandomGet(String url) throws IOException {
    	String result = "";
    	Random random = new Random();
    	int index = random.nextInt(1);
    	int luck = random.nextInt(LIST_SUID.size() - 1);
    	switch(index) {
    	case 0:result = doJsoupGet(url, luck);break;
    	case 1:result = doClientGet(url, luck);break;
//    	case 2:result = doConnectionGet(url, luck);break;
    	}
    	return result;
    }
    
    private static String doJsoupGet(String url, int luck) throws IOException {
    	return Jsoup.connect(url)
    			.cookie("SUID", LIST_SUID.get(luck))
//    			.cookie("SUIR", LIST_SUIR.get(luck))
    			.get()
    			.toString();
    }
  
    @SuppressWarnings("deprecation")
	private static String doClientGet(String url, int luck) throws IOException {
    	CookieStore store = new BasicCookieStore();
//    	store.addCookie(new BasicClientCookie("SUIR", LIST_SUIR.get(luck)));
    	store.addCookie(new BasicClientCookie("SUID", LIST_SUID.get(luck)));
    	getClient().setCookieStore(store);
    	return sendGET(url);
    }
    
    public static void createClient() {
    	try {
			httpClient = new SSLClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    private static String doConnectionGet(String url, int luck) {
    	return getHttpContent(url);
	}
    
    public static void getCookie() {
    	//SUID,SUV,SNUID
    	LIST_SUID.clear();
    	LIST_SUIR.clear();
    	for(int i = 0; i < 10; i++) {
    		createClient();
    		HttpGet httpGet = new HttpGet(WXhelper.getUrl("aaf" + i));
            BasicHttpContext context = new BasicHttpContext();
            httpGet.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            try {
    			CloseableHttpResponse httpResponse = getClient().execute(httpGet, context);
    			List<Cookie> cookies = ((AbstractHttpClient) httpClient).getCookieStore().getCookies();
    			for(Cookie c : cookies) {
    				if("SUID".equals(c.getName().trim())) {
    					LIST_SUID.add(c.getValue());
    				}
    				if("SUIR".equals(c.getName().trim())) {
    					LIST_SUIR.add(c.getValue());
    				}
    			}
    		} catch (ClientProtocolException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} finally {
    			httpGet.releaseConnection();
    			try {
					Thread.sleep(1 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    	
    }
    
    
}
