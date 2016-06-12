package helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import data.CountWX;
import data.WxNews;
import db.MongoWXEntity;
import db.MongoWXEntity.NewsBean;

public class CountHelper {
	
	public static CountWX getCountWx(List<MongoWXEntity> mlist) {
		CountWX wx = new CountWX();
		List<MongoWXEntity> list = mlist;
		sortList(list);
		sortList2(list.get(0).getNews());
		quchong(list.get(0));
		wx.setAvg_read_num(getAvgReadCount(list));
		wx.setHead_read_num(getHeadReadCount(list));
		wx.setMax_read_num(getHeightReadCount(list));
		wx.setTotal_like_num(getAllPraiseCount(list));
		wx.setTotal_read_num(getAllReadCount(list));
		wx.setWeek_update_frequency(getRangeWeek(list));
		wx.setRefer_read_num(getModelofRead(list));
		return wx;
	}
	
	/**
	 * 获取参考阅读数
	 * @return
	 */
	public static int getModelofRead(List<MongoWXEntity> list) {
		sortList(list);
		int count_news = 0;
		int count_read = 0;
		List<MongoWXEntity.NewsBean> list2 = getValueList(list.get(0).getNews());
		for(MongoWXEntity.NewsBean en : list2) {
			count_read += Integer.valueOf(en.getRead_num());
			count_news++;
			if(count_news == 10) {
				break;
			}
		}
		return count_read / count_news;
	}
	
	/**
	 * 获取总点赞数
	 * @return
	 */
	public static int getAllPraiseCount(List<MongoWXEntity> list) {
		sortList(list);
		int count = 0;
		List<MongoWXEntity.NewsBean> list2 = getValueList(list.get(0).getNews());
		for(MongoWXEntity.NewsBean en : list2) {
			count += Integer.valueOf(en.getLike_num());
		}
		return count;
	}
	
	/**
	 * 获取上周头条阅读总数
	 * @return
	 */
	public static int getHeadReadCount(List<MongoWXEntity> list) {
		sortList(list);
		int count = 0;
		List<MongoWXEntity.NewsBean> list2 = getValueList(list.get(0).getNews());
		for(MongoWXEntity.NewsBean en : list2) {
			if(en.getType().equals("1")) {
				count += Integer.valueOf(en.getRead_num());
			}
		}
		return count;
	}
	
	/**
	 * 获取周更新频率
	 * @param list
	 * @return
	 */
	public static int getRangeWeek(List<MongoWXEntity> list) {
		sortList(list);
		int count_head = 0;
		List<MongoWXEntity.NewsBean> list2 = getValueList(list.get(0).getNews());
		for(MongoWXEntity.NewsBean en : list2) {
			if(en.getType().equals("1")) {
				count_head++;
			}
		}
		int number = count_head;
		if(number > 7) {
			number = 7;
		}
		return number;
	}
	
	/**
	 * 得到上一周阅读数平均值
	 * @param list
	 * @return
	 */
	public static int getAvgReadCount(List<MongoWXEntity> list) {
		sortList(list);
		int count = 0;
		List<MongoWXEntity.NewsBean> list2 = getValueList(list.get(0).getNews());
		for(MongoWXEntity.NewsBean en : list2) {
			count += Integer.valueOf(en.getRead_num());
		}
		return count / list2.size();
	}
	
	public static int getHeightReadCount(List<MongoWXEntity> list) {
		sortList(list);
		int height = 0;
		List<MongoWXEntity.NewsBean> list2 = getValueList(list.get(0).getNews());
		for(MongoWXEntity.NewsBean en : list2) {
			if(Integer.valueOf(en.getRead_num()) > height) {
				height = Integer.valueOf(en.getRead_num());
			}
		}
		return height;
	}
	
	public static int getAllReadCount(List<MongoWXEntity> list) {
		sortList(list);
		int count = 0;
		List<MongoWXEntity.NewsBean> list2 = getValueList(list.get(0).getNews());
		for(MongoWXEntity.NewsBean en : list2) {
			count += Integer.valueOf(en.getRead_num());
		}
		return count;
	}
	
	/**
	 * 得到最新的信息
	 * @param list
	 */
	private static  void sortList(List<MongoWXEntity> list) {
		if(list.size() > 1) {
			Collections.sort(list, new Comparator<MongoWXEntity>() {
				@Override
				public int compare(MongoWXEntity o1, MongoWXEntity o2) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						long time_one = format.parse(o1.getTime()).getTime();
						long time_two = format.parse(o2.getTime()).getTime();
						if(time_one > time_two) {
							return -1;
						}
					} catch (ParseException e) {
						return 0;
					}
					return 0;
				}
			});
			
		}
	}
	
	private static  void sortList2(List<NewsBean> list) {
		if(list.size() > 1) {
			Collections.sort(list, new Comparator<NewsBean>() {
				
				@Override
				public int compare(NewsBean o1, NewsBean o2) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					try {
						long time_one = format.parse(o1.getTime()).getTime();
						long time_two = format.parse(o2.getTime()).getTime();
						if(time_one > time_two) {
							return -1;
						}
					} catch (ParseException e) {
						return 0;
					}
					return 0;
				}
			});
			
		}
	}
	
	private static void quchong(MongoWXEntity entity) {
		Iterator<MongoWXEntity.NewsBean> iterator = entity.getNews().iterator();
		List<MongoWXEntity.NewsBean> list_tmp = new ArrayList<>();
		while(iterator.hasNext()) {
			MongoWXEntity.NewsBean bean = new MongoWXEntity.NewsBean();
			if(!list_tmp.contains(bean)) {
				list_tmp.add(iterator.next());
			}
		}
		entity.setNews(list_tmp);
	}
	
	private static List<MongoWXEntity.NewsBean> getValueList(List<MongoWXEntity.NewsBean> list) {
		List<MongoWXEntity.NewsBean> list_tmp = new ArrayList<>();
		list_tmp.addAll(list);
		for(int i = 0; i < list_tmp.size(); i++) {
			long time = Long.valueOf(list_tmp.get(i).getTime());
			if((System.currentTimeMillis() - time) > (7 * 24 * 60 * 60 * 1000)) {
				list_tmp.remove(i);
			}
			
		}
		return list_tmp;
	}
	
}
