package com.example.yichengxuetang.bean;

import java.util.List;

public class CommitQuestionAnswerResponse {

    /**
     * sectionId : 533237262387220773
     * taskList : [{"taskId":"534075293453226971","content":"","answerCodeList":["A"]},{"taskId":"534076316473332193","content":"","answerCodeList":["B"]},{"taskId":"534076637077541519","content":"","answerCodeList":["A","B"]}]
     */

    private String questionId;
    private String batchNo;
    private String totalTime;

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    private String answerContent;
    private List<String> answerCodeList;

    /**
     * taskId : 534075293453226971
     * content :
     * answerCodeList : ["A"]
     */

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }


    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }


    public List<String> getAnswerCodeList() {
        return answerCodeList;
    }

    public void setAnswerCodeList(List<String> answerCodeList) {
        this.answerCodeList = answerCodeList;
    }

}
