package data;

import java.util.List;

public class WxNews {
	
	private AppMsgExtInfoEntity app_msg_ext_info;

	private CommMsgInfoEntity comm_msg_info;
	
	public CommMsgInfoEntity getComm_msg_info() {
        return comm_msg_info;
    }

    public void setComm_msg_info(CommMsgInfoEntity comm_msg_info) {
        this.comm_msg_info = comm_msg_info;
    }

    public AppMsgExtInfoEntity getApp_msg_ext_info() {
        return app_msg_ext_info;
    }

    public void setApp_msg_ext_info(AppMsgExtInfoEntity app_msg_ext_info) {
        this.app_msg_ext_info = app_msg_ext_info;
    }

    public static class CommMsgInfoEntity {
        private long datetime;
        private int type;

        public long getDatetime() {
            return datetime;
        }

        public void setDatetime(long datetime) {
            this.datetime = datetime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public static class AppMsgExtInfoEntity {
    	
        private String title;
        private String digest;
        private int copyright_stat;
        private int is_multi;
        private String content_url;

        private List<MultiAppMsgItemListEntity> multi_app_msg_item_list;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDigest() {
            return digest;
        }

        public void setDigest(String digest) {
            this.digest = digest;
        }

        public int getCopyright_stat() {
            return copyright_stat;
        }

        public void setCopyright_stat(int copyright_stat) {
            this.copyright_stat = copyright_stat;
        }

        public int getIs_multi() {
            return is_multi;
        }

        public void setIs_multi(int is_multi) {
            this.is_multi = is_multi;
        }

        public String getContent_url() {
            return content_url;
        }

        public void setContent_url(String content_url) {
            this.content_url = content_url;
        }

        public List<MultiAppMsgItemListEntity> getMulti_app_msg_item_list() {
            return multi_app_msg_item_list;
        }

        public void setMulti_app_msg_item_list(List<MultiAppMsgItemListEntity> multi_app_msg_item_list) {
            this.multi_app_msg_item_list = multi_app_msg_item_list;
        }

        public static class MultiAppMsgItemListEntity {
            private String title;
            private String digest;
            private String content_url;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDigest() {
                return digest;
            }

            public void setDigest(String digest) {
                this.digest = digest;
            }

            public String getContent_url() {
                return content_url;
            }

            public void setContent_url(String content_url) {
                this.content_url = content_url;
            }
        }
    }
}
