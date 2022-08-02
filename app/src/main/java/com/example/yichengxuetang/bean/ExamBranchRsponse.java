package com.example.yichengxuetang.bean;

public class ExamBranchRsponse {

    /**
     * code : 0
     * message :
     * data : {"batchNo":"20220727-6-1-60","questionId":"593756868037857999","hasCompleted":false,"lastHasCompleted":false,"totalTime":-1}
     */

    private int code;
    private String message;
    /**
     * batchNo : 20220727-6-1-60
     * questionId : 593756868037857999
     * hasCompleted : false
     * lastHasCompleted : false
     * totalTime : -1
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
        private String batchNo;
        private String questionId;
        private boolean hasCompleted;
        private boolean lastHasCompleted;
        private int totalTime;

        public String getBatchNo() {
            return batchNo;
        }

        public void setBatchNo(String batchNo) {
            this.batchNo = batchNo;
        }

        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public boolean isHasCompleted() {
            return hasCompleted;
        }

        public void setHasCompleted(boolean hasCompleted) {
            this.hasCompleted = hasCompleted;
        }

        public boolean isLastHasCompleted() {
            return lastHasCompleted;
        }

        public void setLastHasCompleted(boolean lastHasCompleted) {
            this.lastHasCompleted = lastHasCompleted;
        }

        public int getTotalTime() {
            return totalTime;
        }

        public void setTotalTime(int totalTime) {
            this.totalTime = totalTime;
        }
    }
}
