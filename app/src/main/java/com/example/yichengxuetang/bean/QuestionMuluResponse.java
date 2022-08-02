package com.example.yichengxuetang.bean;

import java.util.List;

public class QuestionMuluResponse {
    /**
     * code : 0
     * message :
     * data : {"totalNum":1088,"doneNum":1,"menus":[{"questionId":"584698855025037455","queationStatus":0},{"questionId":"584694385255928799","queationStatus":0},{"questionId":"584699391497490896","queationStatus":0}]}
     */

    private int code;
    private String message;
    /**
     * totalNum : 1088
     * doneNum : 1
     * menus : [{"questionId":"584698855025037455","queationStatus":0},{"questionId":"584694385255928799","queationStatus":0},{"questionId":"584699391497490896","queationStatus":0}]
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int totalNum;
        private int doneNum;
        /**
         * questionId : 584698855025037455
         * queationStatus : 0
         */

        private List<MenusBean> menus;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getDoneNum() {
            return doneNum;
        }

        public void setDoneNum(int doneNum) {
            this.doneNum = doneNum;
        }

        public List<MenusBean> getMenus() {
            return menus;
        }

        public void setMenus(List<MenusBean> menus) {
            this.menus = menus;
        }

        public static class MenusBean {
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
