package com.example.yichengxuetang.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuestionBankResponse implements Serializable {

    /**
     * code : 0
     * data : [{"doneNum":1,"hasChildren":false,"id":"3","ifLatestExam":true,"name":"言语理解与表达","rightRate":"0.0%","totalNum":94,"totalNumQ":[{"doneNum":1,"hasChildren":false,"id":"3","ifLatestExam":true,"name":"言语理解与表达","rightRate":"0.0%","totalNum":94,"list1":[{"doneNum":1,"hasChildren":false,"id":"3","ifLatestExam":true,"name":"言语理解与表达","rightRate":"0.0%","totalNum":94}]}]}]
     * message :
     */

    private int code;
    private String message;
    /**
     * doneNum : 1
     * hasChildren : false
     * id : 3
     * ifLatestExam : true
     * name : 言语理解与表达
     * rightRate : 0.0%
     * totalNum : 94
     * totalNumQ : [{"doneNum":1,"hasChildren":false,"id":"3","ifLatestExam":true,"name":"言语理解与表达","rightRate":"0.0%","totalNum":94,"list1":[{"doneNum":1,"hasChildren":false,"id":"3","ifLatestExam":true,"name":"言语理解与表达","rightRate":"0.0%","totalNum":94}]}]
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
        private int doneNum;
        private boolean hasChildren;
        private String id;
        private boolean ifLatestExam;
        private String name;
        private String rightRate;
        private int totalNum;
        private int open;

        public int getOpen() {
            return open;
        }

        public void setOpen(int open) {
            this.open = open;
        }

        private ArrayList<Integer> needHiddenIcon;

        public ArrayList<Integer> getNeedHiddenIcon() {
            return needHiddenIcon;
        }

        public void setNeedHiddenIcon(ArrayList<Integer> needHiddenIcon) {
            this.needHiddenIcon = needHiddenIcon;
        }

        /**
         * doneNum : 1
         * hasChildren : false
         * id : 3
         * ifLatestExam : true
         * name : 言语理解与表达
         * rightRate : 0.0%
         * totalNum : 94
         * list1 : [{"doneNum":1,"hasChildren":false,"id":"3","ifLatestExam":true,"name":"言语理解与表达","rightRate":"0.0%","totalNum":94}]
         */

        private List<TotalNumQBean> totalNumQ;

        public int getDoneNum() {
            return doneNum;
        }

        public void setDoneNum(int doneNum) {
            this.doneNum = doneNum;
        }

        public boolean isHasChildren() {
            return hasChildren;
        }

        public void setHasChildren(boolean hasChildren) {
            this.hasChildren = hasChildren;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isIfLatestExam() {
            return ifLatestExam;
        }

        public void setIfLatestExam(boolean ifLatestExam) {
            this.ifLatestExam = ifLatestExam;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRightRate() {
            return rightRate;
        }

        public void setRightRate(String rightRate) {
            this.rightRate = rightRate;
        }

        public int getTotalNum() {
            return totalNum;
        }

        public void setTotalNum(int totalNum) {
            this.totalNum = totalNum;
        }

        public List<TotalNumQBean> getTotalNumQ() {
            return totalNumQ;
        }

        public void setTotalNumQ(List<TotalNumQBean> totalNumQ) {
            this.totalNumQ = totalNumQ;
        }

        public static class TotalNumQBean implements Serializable{
            private int doneNum;
            private boolean hasChildren;
            private String id;
            private boolean ifLatestExam;
            private String name;
            private String rightRate;
            private int totalNum;
            /**
             * doneNum : 1
             * hasChildren : false
             * id : 3
             * ifLatestExam : true
             * name : 言语理解与表达
             * rightRate : 0.0%
             * totalNum : 94
             */

            private List<List1Bean> list1;
            private int open;

            public int getDoneNum() {
                return doneNum;
            }

            public void setDoneNum(int doneNum) {
                this.doneNum = doneNum;
            }

            public boolean isHasChildren() {
                return hasChildren;
            }

            public void setHasChildren(boolean hasChildren) {
                this.hasChildren = hasChildren;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public boolean isIfLatestExam() {
                return ifLatestExam;
            }

            public void setIfLatestExam(boolean ifLatestExam) {
                this.ifLatestExam = ifLatestExam;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRightRate() {
                return rightRate;
            }

            public void setRightRate(String rightRate) {
                this.rightRate = rightRate;
            }

            public int getTotalNum() {
                return totalNum;
            }

            public void setTotalNum(int totalNum) {
                this.totalNum = totalNum;
            }

            public List<List1Bean> getList1() {
                return list1;
            }

            public void setList1(List<List1Bean> list1) {
                this.list1 = list1;
            }

            public void setOpen(int open) {

                this.open = open;
            }

            public int getOpen() {
                return open;
            }

            public static class List1Bean implements Serializable{
                private int doneNum;
                private boolean hasChildren;
                private String id;
                private boolean ifLatestExam;
                private String name;
                private String rightRate;
                private int totalNum;
                private int open;

                public int getDoneNum() {
                    return doneNum;
                }

                public void setDoneNum(int doneNum) {
                    this.doneNum = doneNum;
                }

                public boolean isHasChildren() {
                    return hasChildren;
                }

                public void setHasChildren(boolean hasChildren) {
                    this.hasChildren = hasChildren;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public boolean isIfLatestExam() {
                    return ifLatestExam;
                }

                public void setIfLatestExam(boolean ifLatestExam) {
                    this.ifLatestExam = ifLatestExam;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getRightRate() {
                    return rightRate;
                }

                public void setRightRate(String rightRate) {
                    this.rightRate = rightRate;
                }

                public int getTotalNum() {
                    return totalNum;
                }

                public void setTotalNum(int totalNum) {
                    this.totalNum = totalNum;
                }

                public int getOpen() {
                    return open;
                }

                public void setOpen(int open) {
                    this.open = open;
                }
            }
        }
    }
}
