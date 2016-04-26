package helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AgencyHelper {
	
	private static final String URL_MAIN = "http://www.xicidaili.com/nt/";
	
	public static void getHostList(final int maxPage, final HostLoadListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				List<HttpHost> hosts = new ArrayList<>();
				int page = 1;
				while(page <= maxPage) {
					try {
						listener.onProgress(page);
						Document doc = Jsoup.connect(URL_MAIN + page)
								.userAgent("Mozilla")
								.cookie("auth", "token")
								.timeout(7000)
								.get();
						Element ele = doc.getElementsByTag("tbody").get(0);
						boolean isFirst = true;
						for(Element e : ele.getElementsByTag("tr")) {
							
							String ip = null;
							String port = null;
							Elements eles = e.getElementsByTag("td");
							if(isFirst) {
								isFirst = false;
							} else {
								for(int i = 0; i < eles.size(); i++) {
									if(i == 2) {
										ip = eles.get(i).text();
									}
									if(i == 3) {
										port = eles.get(i).text();
									}
								}
								if(ip != null) {
									hosts.add(new HttpHost(ip, Integer.valueOf(port)));
								}
							}
							
						}
						page++;
					} catch (IOException e) {
						listener.onFailed();
					}
				}
				listener.onLoadFinish(hosts);
			}
		}).start();
	
	}
	
	public interface HostLoadListener {
		public void onLoadFinish(List<HttpHost> list);
		public void onFailed();
		public void onProgress(int page);
	}
	
}
