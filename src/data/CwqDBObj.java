package data;

public class CwqDBObj {
	//表
	public static final String TABLE_WEIXIN = "wlf_weixin_media";
	//基本信息
	public static final String KEY_ACCOUNT = "weixin_account";
	public static final String KEY_NAME = "weixin_text";
	public static final String KEY_UID = "uid";
	public static final String KEY_FANS = "fans";
	public static final String KEY_AVATAR = "avatar";
	public static final String KEY_PROVINCE = "province";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_TIME = "create_time";
	public static final String KEY_WEEK_READ = "week_read_count";
	public static final String KEY_QR_CODE = "qr_code";
	
	//软广
	public static final String KEY_TOP = "single_price";
	public static final String KEY_ONE = "multiple_one_price";
	public static final String KEY_TWO = "multiple_two_price";
	public static final String KEY_THREE = "multiple_three_price";
	//软广处理
	public static final String KEY_R_TOP = "single_real_price";
	public static final String KEY_R_ONE = "multiple_one_real_price";
	public static final String KEY_R_TWO = "multiple_two_real_price";
	public static final String KEY_R_THREE = "multiple_three_real_price";
	//硬广
	public static final String KEY_D_TOP = "ad_" + KEY_TOP;
	public static final String KEY_D_ONE = "ad_" + KEY_ONE;
	public static final String KEY_D_TWO = "ad_" + KEY_TWO;
	public static final String KEY_D_THREE = "ad_" + KEY_THREE;
	//硬广处理
	public static final String KEY_DR_TOP = "ad_" + KEY_R_TOP;
	public static final String KEY_DR_ONE = "ad_" + KEY_R_ONE;
	public static final String KEY_DR_TWO = "ad_" + KEY_R_TWO;
	public static final String KEY_DR_THREE = "ad_" + KEY_R_THREE;
	
}
