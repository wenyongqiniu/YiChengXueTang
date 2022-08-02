package com.example.yichengxuetang.bean;

public class InterruptOnlyResponse {
    /**
     * code : 0
     * message :
     * data : {"notDoneNum":1087,"doneNum":1,"wrongNum":0,"rightRate":"100.0%"}
     */

    private int code;
    private String message;
    /**
     * notDoneNum : 1087
     * doneNum : 1
     * wrongNum : 0
     * rightRate : 100.0%
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
        private int notDoneNum;
        private int doneNum;
        private int wrongNum;
        private int rightNum;
        private int totalNum;
        private String rightRate;
        private String totalTime;

        public int getRightNum() {
            return rightNum;
        }

        public void setRightNum(int rightNum) {
            this.rightNum = rightNum;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public String getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(String totalTime) {
            this.totalTime = totalTime;
        }

        public int getNotDoneNum() {
            return notDoneNum;
        }

        public void setNotDoneNum(int notDoneNum) {
            this.notDoneNum = notDoneNum;
        }

        public int getDoneNum() {
            return doneNum;
        }

        public void setDoneNum(int doneNum) {
            this.doneNum = doneNum;
        }

        public int getWrongNum() {
            return wrongNum;
        }

        public void setWrongNum(int wrongNum) {
            this.wrongNum = wrongNum;
        }

        public String getRightRate() {
            return rightRate;
        }

        public void setRightRate(String rightRate) {
            this.rightRate = rightRate;
        }
    }
}
