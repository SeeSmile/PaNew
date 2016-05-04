package main;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.TextPage;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import utils.SwebUtil;
import utils.WebUtil;

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/*
		IF_ACTION	
		devrestart
		IF_ERRORPARAM	
		SUCC
		IF_ERRORSTR	
		SUCC
		IF_ERRORTYPE	
		-1
		flag	
		1*/
		try {
			WebUtil.restart2();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println(WmdebUtil.pingIP());
	}

}
