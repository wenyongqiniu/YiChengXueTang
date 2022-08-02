package com.example.yichengxuetang.bean;

import java.util.ArrayList;
import java.util.List;

public class QuestionSecondkResponse {


    private String id;
    private String name;
    private String getRightRate;
    private boolean ifLatestExam;
    private boolean hasChildren;
    private int totalNum;
    private int doneNum;
    private List<QuestionBankResponse> data;

    public List<QuestionBankResponse> getData() {
        return data;
    }

    public void setData(List<QuestionBankResponse> data) {
        this.data = data;
    }

    public String getGetRightRate() {
        return getRightRate;
    }

    public void setGetRightRate(String getRightRate) {
        this.getRightRate = getRightRate;
    }

    private ArrayList<Integer> needHiddenIcon;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIfLatestExam() {
        return ifLatestExam;
    }

    public void setIfLatestExam(boolean ifLatestExam) {
        this.ifLatestExam = ifLatestExam;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

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

    public ArrayList<Integer> getNeedHiddenIcon() {
        return needHiddenIcon;
    }

    public void setNeedHiddenIcon(ArrayList<Integer> needHiddenIcon) {
        this.needHiddenIcon = needHiddenIcon;
    }

}
