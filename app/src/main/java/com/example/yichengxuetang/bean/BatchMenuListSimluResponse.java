package com.example.yichengxuetang.bean;

import java.io.Serializable;
import java.util.List;

public class BatchMenuListSimluResponse implements Serializable {
    /**
     * code : 0
     * message :
     * data : [{"typeName":"常识判断（单选）","menus":[{"questionId":"593895469572707052","queationStatus":0},{"questionId":"591721801077773272","queationStatus":0}]},{"typeName":"常识判断（多选）","menus":[{"questionId":"591230738298852267","queationStatus":0},{"questionId":"593772830392864932","queationStatus":0}]},{"typeName":"资料分析","menus":[{"questionId":"589193570659357109","queationStatus":0},{"questionId":"589194678748337113","queationStatus":0}]},{"typeName":"判断推理","menus":[{"questionId":"587605141517521392","queationStatus":0},{"questionId":"587631414339982309","queationStatus":0}]},{"typeName":"数量关系","menus":[{"questionId":"586590889872740996","queationStatus":0},{"questionId":"587346235214025629","queationStatus":0}]},{"typeName":"言语理解与表达","menus":[{"questionId":"584810897107079389","queationStatus":0},{"questionId":"584811606682653259","queationStatus":0}]}]
     */

    private int code;
    private String message;
    /**
     * typeName : 常识判断（单选）
     * menus : [{"questionId":"593895469572707052","queationStatus":0},{"questionId":"591721801077773272","queationStatus":0}]
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String typeName;
        /**
         * questionId : 593895469572707052
         * queationStatus : 0
         */

        private List<MenusBean> menus;

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public List<MenusBean> getMenus() {
            return menus;
        }

        public void setMenus(List<MenusBean> menus) {
            this.menus = menus;
        }

        public static class MenusBean implements Serializable{
            private String questionId;
            private int queationStatus;

            public String getQuestionId() {
                return questionId;
            }

            public void setQuestionId(String questionId) {
                this.questionId = questionId;
            }

            public int getQueationStatus() {
                return queationStatus;
            }

            public void setQueationStatus(int queationStatus) {
                this.queationStatus = queationStatus;
            }
        }
    }
}
