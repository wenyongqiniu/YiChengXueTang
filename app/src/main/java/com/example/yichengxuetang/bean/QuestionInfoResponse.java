package com.example.yichengxuetang.bean;

import java.util.List;

public class QuestionInfoResponse {
    /**
     * code : 0
     * message :
     * data : {"totalNum":1088,"currentNum":332,"id":"584698855025037455","question":"<p>环境保护部部长在回答记者提问时说，我国传统的污染物排放量仍然很大，超过我国的环境容量，致使一些地区环境容量达不到国家规定的标准。随着经济的快速发展，一些新的环境问题不断产生，特别是危险化学品、持久性有机污染物、电子垃圾等。我国水和大气的环境问题还没有完全解决好，土壤的污染现状又凸显在我们的而前。<\/p><p>根据以上信息，可以得出的结论是<u>      <\/u>。<\/p>","questionType":1,"rightAnswer":"A","customerAnswer":"B","analysis":"<ul><li><fb-ng-solution-detail-item><p><fb-ng-solution-detail-content><\/fb-ng-solution-detail-content><\/p><p>文段开篇指出我国传统的污染物排放量仍然很大，一些地区已经超标，即传统的污染物排放没有解决；之后又介绍了\u201c随着经济的快速发展，一些新的环境问题不断产生\u201d，并进行举例说明，A项可概括全文内容，为正确答案。<\/p><p>B项\u201c新的环境问题\u201d对应文段部分内容，表述片面且\u201c必然产物\u201d表述绝对，排除；<\/p><p>C项，传统的污染物排放量大致使一些地区环境容量超标，仅对应首句，表述片面，排除；<\/p><p>D项，为无中生有的表述，排除。<\/p><p>故正确答案为A。<\/p><p>【文段出处】《中国环境形势依然很严峻 突出表现在三个方面》<\/p><\/fb-ng-solution-detail-item><\/li><li><h4>考点<\/h4><ul><li><fb-ng-solution-detail-keypoint><p>言语理解与表达 > 阅读理解 > 中心理解题<\/p><\/fb-ng-solution-detail-keypoint><\/li><\/ul><\/li><\/ul>","nextId":"584699391497490896","prevId":"584694385255928799","answerList":[{"id":"584698855490605208","answerCode":"A","answer":"传统的污染物排放没有解决，新的环境问题又层出不穷，中国环境问题面临严峻形势"},{"id":"584703012972810698","answerCode":"C","answer":"我国一些地区环境质量达不到国家规定的标准，是因为传统的污染物排放量大"},{"id":"584703012993782220","answerCode":"B","answer":"新的环境问题是经济快速发展的必然产物"},{"id":"584703013077668302","answerCode":"D","answer":"环境污染问题的呈现有它的特殊规律，即先出现水和大气的污染，再出现土壤的污染"}]}
     */

    private int code;
    private String message;
    /**
     * totalNum : 1088
     * currentNum : 332
     * id : 584698855025037455
     * question : <p>环境保护部部长在回答记者提问时说，我国传统的污染物排放量仍然很大，超过我国的环境容量，致使一些地区环境容量达不到国家规定的标准。随着经济的快速发展，一些新的环境问题不断产生，特别是危险化学品、持久性有机污染物、电子垃圾等。我国水和大气的环境问题还没有完全解决好，土壤的污染现状又凸显在我们的而前。</p><p>根据以上信息，可以得出的结论是<u>      </u>。</p>
     * questionType : 1
     * rightAnswer : A
     * customerAnswer : B
     * analysis : <ul><li><fb-ng-solution-detail-item><p><fb-ng-solution-detail-content></fb-ng-solution-detail-content></p><p>文段开篇指出我国传统的污染物排放量仍然很大，一些地区已经超标，即传统的污染物排放没有解决；之后又介绍了“随着经济的快速发展，一些新的环境问题不断产生”，并进行举例说明，A项可概括全文内容，为正确答案。</p><p>B项“新的环境问题”对应文段部分内容，表述片面且“必然产物”表述绝对，排除；</p><p>C项，传统的污染物排放量大致使一些地区环境容量超标，仅对应首句，表述片面，排除；</p><p>D项，为无中生有的表述，排除。</p><p>故正确答案为A。</p><p>【文段出处】《中国环境形势依然很严峻 突出表现在三个方面》</p></fb-ng-solution-detail-item></li><li><h4>考点</h4><ul><li><fb-ng-solution-detail-keypoint><p>言语理解与表达 > 阅读理解 > 中心理解题</p></fb-ng-solution-detail-keypoint></li></ul></li></ul>
     * nextId : 584699391497490896
     * prevId : 584694385255928799
     * answerList : [{"id":"584698855490605208","answerCode":"A","answer":"传统的污染物排放没有解决，新的环境问题又层出不穷，中国环境问题面临严峻形势"},{"id":"584703012972810698","answerCode":"C","answer":"我国一些地区环境质量达不到国家规定的标准，是因为传统的污染物排放量大"},{"id":"584703012993782220","answerCode":"B","answer":"新的环境问题是经济快速发展的必然产物"},{"id":"584703013077668302","answerCode":"D","answer":"环境污染问题的呈现有它的特殊规律，即先出现水和大气的污染，再出现土壤的污染"}]
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
        private int currentNum;
        private String id;
        private String question;
        private int questionType;
        private String rightAnswer;
        private String customerAnswer;
        private String analysis;
        private String nextId;
        private String prevId;
        private boolean HasCollected;

        public boolean isHasCollected() {
            return HasCollected;
        }

        public void setHasCollected(boolean hasCollected) {
            HasCollected = hasCollected;
        }

        /**
         * id : 584698855490605208
         * answerCode : A
         * answer : 传统的污染物排放没有解决，新的环境问题又层出不穷，中国环境问题面临严峻形势
         */

        private List<AnswerListBean> answerList;

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public int getCurrentNum() {
            return currentNum;
        }

        public void setCurrentNum(int currentNum) {
            this.currentNum = currentNum;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public int getQuestionType() {
            return questionType;
        }

        public void setQuestionType(int questionType) {
            this.questionType = questionType;
        }

        public String getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(String rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        public String getCustomerAnswer() {
            return customerAnswer;
        }

        public void setCustomerAnswer(String customerAnswer) {
            this.customerAnswer = customerAnswer;
        }

        public String getAnalysis() {
            return analysis;
        }

        public void setAnalysis(String analysis) {
            this.analysis = analysis;
        }

        public String getNextId() {
            return nextId;
        }

        public void setNextId(String nextId) {
            this.nextId = nextId;
        }

        public String getPrevId() {
            return prevId;
        }

        public void setPrevId(String prevId) {
            this.prevId = prevId;
        }

        public List<AnswerListBean> getAnswerList() {
            return answerList;
        }

        public void setAnswerList(List<AnswerListBean> answerList) {
            this.answerList = answerList;
        }

        public static class AnswerListBean {
            private String id;
            private String answerCode;
            private String answer;
            private boolean checked;
            private boolean selected;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getAnswerCode() {
                return answerCode;
            }

            public void setAnswerCode(String answerCode) {
                this.answerCode = answerCode;
            }

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public void setSelected(boolean selected) {
                this.selected = selected;
            }

            public boolean getSelected() {
                return selected;
            }
        }
    }
}
