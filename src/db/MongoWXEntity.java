package db;

import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;

import data.CountWX;

/**
 * describe:
 * notice:
 * Created by FuPei on 2016/5/26.
 */
public class MongoWXEntity{

  
	/**
     * sid : 404713389
     * time : 2016-05-05 00:00:00
     * news : [{"title":"互联网广告违法率是传统媒体3倍以上 医疗广告是&quot;重灾区&quot;","time":"1462373208","subtitle":"记者近日从国家工商总局获悉，2015年，国家互联网广告监测中心对全国27个省和9个重点城市的169家网","read_num":"2638","like_num":"15","fileid":"507144029","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgfepmJgPJ47POLQpWfgnI2OLyts3QQVbPte01*Ipi4jDZKZiT5CR1yCX83V53x7lzxua1tclL2nFb9rvB8h0*Ww="},{"title":"移动互联网广告现状：是什么 为什么","time":"1462373208","subtitle":"移动广告到底是怎么回事？乱究竟是乱了什么？这个问题，随便找一个移动互联网业者都能说上个一二。但是一直没","read_num":"255","like_num":"14","fileid":"507144030","type":"2","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgfepmJgPJ47POLQpWfgnI2N4jMUo85GhNRh2nAXc7Qa569q2gMxct4NAsbBgJQ*yKSSAXFbqOyRa2P4kdnRXt2M="},{"title":"【互联网】十五个大开脑洞的广告","time":"1462373208","subtitle":" 好的创意就是一味最好的调味料，它令平凡的广告成为受众者脑中最美味的精神食粮，久久品味。掌中宝为您带来15个","read_num":"228","like_num":"13","fileid":"507144034","type":"3","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgfepmJgPJ47POLQpWfgnI2MkwuRZccd8NNZQeowZb6A44wuTtFJIh0-xYO0PFyBlZQdfqLCmZNRq6kghALwOexM="},{"title":"新广告时代：互联网技术对广告业的影响如同海啸","time":"1461685097","subtitle":" 2016年4月19日下午，由中信出版集团和广告门联合主办的\u201c转型与升级：互联网时代的广告新生态\u2014\u2014暨 《广","read_num":"2660","like_num":"15","fileid":"507144024","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgeKuPbpVLiNXIQkq4swzBQRu5N6-Tt2WP-3wwG0G*h4-lntE30rWb6VIQQ4gBGUyiIQPqxCx3lTNTqKZaZmFL08="},{"title":"互联网时代，为何大品牌还是偏爱传统广告？","time":"1461685097","subtitle":" 互联网广告存在重大缺陷假货横行PC盛行的时代，互联网广告的隐蔽性为各类虚假产品宣传提供了土壤。是不是想起了","read_num":"233","like_num":"12","fileid":"507144025","type":"2","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgeKuPbpVLiNXIQkq4swzBQSK2Diergi9zyLq*CPiQLEpkYha6Zs*ZzVEjBI6ycRpj5MdvAtCcEjDk0UV1jFq2MY="},{"title":"17部委出台互联网金融广告整治方案,禁9大类","time":"1461685097","subtitle":"  据报道，为期一年的互联网金融领域专项整治将在全国范围内启动。互联网金融企业广告治理作为整治行动的一环，日","read_num":"263","like_num":"15","fileid":"507144026","type":"3","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgeKuPbpVLiNXIQkq4swzBQRoU7rYL*t*WpMBbVLs*BmyM5yE-CrpaugOLsoaxcWgqytcjl8LSSPFVQrqz07dnQY="},{"title":"这可能是朋友圈第一个被封掉的H5游戏","time":"1460630779","subtitle":" 微信要整治朋友圈营销这个H5居然包含了所有会被封掉的元素想知道是什么H5？趁现在点击 ","read_num":"4709","like_num":"238","fileid":"407143379","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgeiY1vLyj8LDSjbHDcr-rS8VDa3m*9Kb9pYuJ2N0SpjSxHtVrPwuwdVBdKgcTWSw1xbcfwCR2Q52xjLXCvEg0m4="},{"title":"那些不可错过的广告创意","time":"1460630779","subtitle":"在我们现在资讯泛滥的时代，不管往哪里看，都有广告想要赢得我们的注意力。有创意的广告则是一家公司或是品牌最重要","read_num":"1291","like_num":"20","fileid":"407143463","type":"2","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgeiY1vLyj8LDSjbHDcr-rS9*e5ua7CqqnXFTCk00FcTUxL1HnoEiPADVrvN-QvhvSLUxPyDGQIEnn*p*n-h4TBA="},{"title":"那些脑洞大开的汽车广告创意","time":"1460630779","subtitle":"汽车行业牵涉的范围很广泛，包括了设计、开发、制造、营销以及销售。它是世界上最重要的经济收入组成。在全世界，大","read_num":"378","like_num":"6","fileid":"407143538","type":"3","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgeiY1vLyj8LDSjbHDcr-rS*EOsWNfNoN*YVDEz25NzcY5xAHqb4k4W2lKl4iw9s58mrguMIZxaDxGd5mTsNX8ec="},{"title":"【资讯】互联网时代户外广告的发展","time":"1460472472","subtitle":" 互联网的高速发展正在改变现有的人类生活方式，而在各种变化中，首当其冲的是传统的各类传播平台。关于互联网对传","read_num":"3349","like_num":"39","fileid":"406199841","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgeqmBAU4hHfkq6dq7qNpYTSwSBTVwgwPlxyAAnF60*jpAxM7PQqG*688XHv3*YQVjpfvClZit6tjdX*IpZ-rme0="},{"title":"市场运营高手教你8招玩转互联网广告！","time":"1460472472","subtitle":"近年来各大网络平台的兴起让很多人看准了互联网广告这一大块肥肉，作为一种全新的广告媒体，互联网广告的速度快效果","read_num":"434","like_num":"20","fileid":"406199873","type":"2","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgeqmBAU4hHfkq6dq7qNpYTT5wkvanCQatpmXayT4mwzxzdHjNvavKLaUgsfINBkzFL4HEI4328j7rgrUhwem3Cc="},{"title":"互联网广告和屏蔽插件的恩怨","time":"1460472472","subtitle":"屏蔽广告插件\\/软件是一个\u201c安静\u201d而不可忽视的存在。当它正常工作时，你几乎注意不到它。要是没有它，一惊一乍的网","read_num":"231","like_num":"12","fileid":"406199938","type":"3","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgeqmBAU4hHfkq6dq7qNpYTQrlCPfE8prMcSz4yCEl1b-LEcwqjoUPG9J3tfcHJPEvy*4epO0xyxntCnwXs2vbUE="},{"title":"车险费改全面铺开 互联网车险面临\u201c双刃剑\u201d","time":"1460042381","subtitle":"车险费改破冰前行，全面铺开亦近在眼前。在此风口下，互联网车险同时面临机遇与挑战的双刃剑。机遇主要来源于两方面","read_num":"3146","like_num":"46","fileid":"401128764","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgTMJxBK0F9jh8k5OONXI454vT4G28Y9PlqGgnwNevjuCrqN4vwahet84lwKIt*7oX*VJPmfmEf-5mW6ZCgoIJ2g="},{"title":"互联网人做餐饮？别是凶多吉少！","time":"1460042381","subtitle":"自从O2O兴起后，大量的互联网人进入传统行业，然而颠覆和改造的使命尚未完成，却已有相当多的创业企业先阵亡了。","read_num":"429","like_num":"22","fileid":"401128770","type":"2","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgTMJxBK0F9jh8k5OONXI457WlISIpfk93zauiRQM9uL5PjAUChtfQZOpIr7ZM0Qe1qHiB2oOAJV9cR78ymfmPI4="},{"title":"\u201c十三五\u201d将实施\u201c互联网+智慧能源\u201d行动计划","time":"1460042381","subtitle":"在11月11日召开的全国\u201c十三五\u201d能源规划工作座谈会上，国家能源局明确提出，\u201c十三五\u201d将实施\u201c互联网+智慧能","read_num":"226","like_num":"14","fileid":"401128779","type":"3","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgTMJxBK0F9jh8k5OONXI457BBCD1FF0aFxUf3RLvrXirpWAJ6wv6ufDDtaSBwy6t0AE8xOjin0*dTMeYqkK1TDI="},{"title":"提前一年 全球互联网广告明年就将超过电视广告","time":"1459435964","subtitle":"今年网络广告增速将是其他广告行业的3倍，其增长动力主要有3个，分别是社交网络广告、视频广告和搜索广告。腾讯科","read_num":"3051","like_num":"52","fileid":"405652279","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgcOeHWh2yk0BN96SHBLsdl7UmeyPj-qJKxxVQq-sOi46Lygs5O2xjZulc7IJVDg7PwXAbKI3gm-Zqxamo7uBNKI="},{"title":"互联网广告2016年或占中国广告半壁江山","time":"1459435964","subtitle":"　日前，市场研究公司GroupM的最新报告显示，互联网广告今年有望占据中国广告总开支的48.7%，而这一数字","read_num":"449","like_num":"19","fileid":"405652284","type":"2","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgcOeHWh2yk0BN96SHBLsdl6H5IWer5uaQ-6WYuvLf1H5jWVL58WNwyuQsulSzQsmqtMI6qtXzzj2grf2jCduAd4="},{"title":"互联网营销的产品网络广告投放技巧","time":"1459435964","subtitle":"广告作为企业营销的一种重要手段，它具有传播信息、诱导和说服、沟通供求、塑造企业形象和促进销售的功能，在市场经","read_num":"244","like_num":"11","fileid":"405652287","type":"3","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgcOeHWh2yk0BN96SHBLsdl6C4lROMAXzpGYVUYM6y4SV8WDlRwXIps59l4vvdXCiI8jkyEoXFtrwbtTFerDzsuM="},{"title":"重磅：必胜客上演\u201c绿色\u201d大跨界 透露品牌3大信号","time":"1459349258","subtitle":"跨界合作如今是一个非常\u201c潮流\u201d的字眼，各行各业都在寻找着跨界合作的契机，并且乐此不疲，原因是简直屡试不爽，","read_num":"10570","like_num":"245","fileid":"405647687","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgfayaS6c6ZE1am9O0cjrMubIIRYmUKTzOHrpLOKShI6Cx2U*dcB0d0wP-0eKMYiMUbxsg5TbZljOCLwxJTM8JuQ="},{"title":"\u201c照骗\u201d中的热门景区","time":"1459349258","subtitle":"又到了春暖花开的好时节，许多上班族打算借着大好春光踏上外出游玩的旅途，许多景区的美照就成了驴友们的必备参考物","read_num":"605","like_num":"9","fileid":"405647792","type":"2","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgfayaS6c6ZE1am9O0cjrMuZdajMIf3YbiErysz*ScFlaUO7Vqexpc1x3w9bQ7oEfG*YM08ckuAL2*ARfNYZrl-0="},{"title":"这才是西藏真正的古村落，别去错了","time":"1459349258","subtitle":"原来，离拉萨市不远，有一片硕大的油菜花海；原来，出拉萨，往尼木县吞巴乡，有一条水磨走廊；原来，在昌都然乌湖边","read_num":"313","like_num":"5","fileid":"405648031","type":"3","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgfayaS6c6ZE1am9O0cjrMuYfkHrmkqbM8TX2p5ngz*xtooZPBHxMu2aTNICXgiCOtaciM6lNQeDaWdbHLiwHs5g="},{"title":"优酷土豆携手《京剧猫》进军国产动画新世界","time":"1458916669","subtitle":" 1月13日，优酷土豆在京举办动漫战略发布会，会上除了宣布未来每年将投入5亿元，支持动漫产业持续性地孵化国产","read_num":"1137","like_num":"43","fileid":"405402542","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgfiMtqaG2b0MbhT0ysJd76AOumECD0l5CpIJovgVaBrckJLX6WgqCupgUtB9wraQE*hmN6Sx*0fSBqW3suh05kc="},{"title":"《太阳的后裔》有毒 揭秘女神和鲜肉的不老青春","time":"1457616778","subtitle":"如果你最近遇到有人在花痴宋慧乔女神和宋仲基老公的，不要惊讶，他们是中了韩剧《太阳的后裔》的毒。","read_num":"5907","like_num":"299","fileid":"404789854","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgexMyg1ecYyQ8Bub079v5RX7Lwprw*tJXWfykSlClBsMniCOHPSmWOut1pE5BaY-CsrGcVkheFftrCIXgVV4q*s="},{"title":"寒冬里，数据说哪个互联网职位有前途？","time":"1457447718","subtitle":"看完后，我和我的小伙伴都不好了。桑心的话不多说，直接用数据说话。数据来源于专注互联网招聘的垂直领域网站-拉勾","read_num":"1201","like_num":"19","fileid":"404106767","type":"1","url":"http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgQtCggRWfS23cwJ6*LpGx9Mjmzqroey51pJz8z6ViFKQZ*afsq1G*JBt1xN8WLvwb94wmYDv3SiGoLtWZ85Iwy0="}]
     * is_att : 2
     * qrcode : http://mp.weixin.qq.com/rr?src=3&timestamp=1462510532&ver=1&signature=QsxrBAeHW93u1cRMKyerF3FgV6YS4TDHAp7MKonqsziFmS8sHVB5OGaOt*0g0zmIy1EPYhYBex7pSphcHFPxe3McbHvOxNGJUq5qunicCiA=
     * name : 互联网广告杂谈
     * state : {"$numberLong":"1"}
     * account : hlwggzt
     * avatar : http://wx.qlogo.cn/mmhead/Q3auHgzwzM7Q4yhibfKheRp0QL1icE22z2icTEc8kiattoEhibQaHExEfgA/0
     * url : http://mp.weixin.qq.com/profile?src=3&timestamp=1462510532&ver=1&signature=KtBUYto*GbWj9mdxxA5Ojg7pySauvZY1UMl1WVBD22e7ecSBZ9RUd5O3Rz-7rQN0Nj2-nicJeT8ShPm0fgpp0Q==
     */

	private int refer_read_num;
	private int week_update_frequency;
	private int total_read_num;
	private int max_read_num;
	private int avg_read_num;
	private int head_read_num;
	private int total_like_num;
	
	
	public int getRefer_read_num() {
		return refer_read_num;
	}

	public void setRefer_read_num(int refer_read_num) {
		this.refer_read_num = refer_read_num;
	}

	public int getWeek_update_frequency() {
		return week_update_frequency;
	}

	public void setWeek_update_frequency(int week_update_frequency) {
		this.week_update_frequency = week_update_frequency;
	}

	public int getTotal_read_num() {
		return total_read_num;
	}

	public void setTotal_read_num(int total_read_num) {
		this.total_read_num = total_read_num;
	}

	public int getMax_read_num() {
		return max_read_num;
	}

	public void setMax_read_num(int max_read_num) {
		this.max_read_num = max_read_num;
	}

	public int getAvg_read_num() {
		return avg_read_num;
	}

	public void setAvg_read_num(int avg_read_num) {
		this.avg_read_num = avg_read_num;
	}

	public int getHead_read_num() {
		return head_read_num;
	}

	public void setHead_read_num(int head_read_num) {
		this.head_read_num = head_read_num;
	}

	public int getTotal_like_num() {
		return total_like_num;
	}

	public void setTotal_like_num(int total_like_num) {
		this.total_like_num = total_like_num;
	}

	public void setCount(CountWX wx) {
		setTotal_like_num(wx.getTotal_like_num());
		setAvg_read_num(wx.getAvg_read_num());
		setHead_read_num(wx.getHead_read_num());
		setRefer_read_num(wx.getRefer_read_num());
		setTotal_read_num(wx.getTotal_read_num());
		setWeek_update_frequency(wx.getWeek_update_frequency());
		setMax_read_num(wx.getMax_read_num());
	}

	private String index_status;
    private String sid;
    private String time;
    private String is_att;
    private String qrcode;
    private String name;
    /**
     * $numberLong : 1
     */
    private String account;
    private String avatar;
    private String url;
    /**
     * title : 互联网广告违法率是传统媒体3倍以上 医疗广告是&quot;重灾区&quot;
     * time : 1462373208
     * subtitle : 记者近日从国家工商总局获悉，2015年，国家互联网广告监测中心对全国27个省和9个重点城市的169家网
     * read_num : 2638
     * like_num : 15
     * fileid : 507144029
     * type : 1
     * url : http://mp.weixin.qq.com/s?timestamp=1462510532&src=3&ver=1&signature=oeM4Fy9iWLsH8zbIkDLsedBcog8LCmKo7mbyHzmei5antnkNcpxP*WyWO9*cZexErLD8HYHHAfamyUztGOdYgfepmJgPJ47POLQpWfgnI2OLyts3QQVbPte01*Ipi4jDZKZiT5CR1yCX83V53x7lzxua1tclL2nFb9rvB8h0*Ww=
     */

    private List<NewsBean> news;

//    private CountWX count;
    
    private String state;
    
    
    
    public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
//
//	public CountWX getCount() {
//		return count;
//	}
//
//	public void setCount(CountWX count) {
//		this.count = count;
//	}

	public String getIndex_status() {
		return index_status;
	}

	public void setIndex_status(String index_status) {
		this.index_status = index_status;
	}

	public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIs_att() {
        return is_att;
    }

    public void setIs_att(String is_att) {
        this.is_att = is_att;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<NewsBean> getNews() {
        return news;
    }

    public void setNews(List<NewsBean> news) {
        this.news = news;
    }

    @Override
    public String toString() {
    	return new Gson().toJson(this);
    }
 

    public static class NewsBean {
    	
        private String title;
        private String time;
        private String subtitle;
        private String read_num;
        private String like_num;
        private String fileid;
        private String type;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getRead_num() {
            return read_num;
        }

        public void setRead_num(String read_num) {
            this.read_num = read_num;
        }

        public String getLike_num() {
            return like_num;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public String getFileid() {
            return fileid;
        }

        public void setFileid(String fileid) {
            this.fileid = fileid;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
        
        @Override
        public String toString() {
        	return new Gson().toJson(this);
        }
    }
}
