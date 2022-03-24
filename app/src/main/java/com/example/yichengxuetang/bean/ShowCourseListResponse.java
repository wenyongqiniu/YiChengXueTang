package com.example.yichengxuetang.bean;

import java.util.List;

public class ShowCourseListResponse {

    /**
     * code : 0
     * message :
     * data : {"xbCourseList":[{"packageId":"202008201946481588","courseId":"531847770749244411","classId":"558619293577134667","courseName":"淘宝无货源训练营","courseType":0,"currentDay":0,"totalDay":5,"customerNum":0}],"bigCourse":{"contractStatus":1,"selectClassStatus":1,"addressStatus":1,"showTeacherStatus":1,"startTime":"2022-03-26","packageName":"淘宝无货源赚钱技能课","className":"2期1班","teacherQrCode":"https://image.xicaishe.com/20220218151823EmWE41pu.jpg","bigCourseList":[{"courseId":"533305521744614302","courseName":"淘宝无货源赚钱技能课","studyStatus":0,"coverImage":"https://image.xicaishe.com/20220111175303cXnn5ERQ.png"}]}}
     */

    private int code;
    private String message;
    /**
     * xbCourseList : [{"packageId":"202008201946481588","courseId":"531847770749244411","classId":"558619293577134667","courseName":"淘宝无货源训练营","courseType":0,"currentDay":0,"totalDay":5,"customerNum":0}]
     * bigCourse : {"contractStatus":1,"selectClassStatus":1,"addressStatus":1,"showTeacherStatus":1,"startTime":"2022-03-26","packageName":"淘宝无货源赚钱技能课","className":"2期1班","teacherQrCode":"https://image.xicaishe.com/20220218151823EmWE41pu.jpg","bigCourseList":[{"courseId":"533305521744614302","courseName":"淘宝无货源赚钱技能课","studyStatus":0,"coverImage":"https://image.xicaishe.com/20220111175303cXnn5ERQ.png"}]}
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
        /**
         * contractStatus : 1
         * selectClassStatus : 1
         * addressStatus : 1
         * showTeacherStatus : 1
         * startTime : 2022-03-26
         * packageName : 淘宝无货源赚钱技能课
         * className : 2期1班
         * teacherQrCode : https://image.xicaishe.com/20220218151823EmWE41pu.jpg
         * bigCourseList : [{"courseId":"533305521744614302","courseName":"淘宝无货源赚钱技能课","studyStatus":0,"coverImage":"https://image.xicaishe.com/20220111175303cXnn5ERQ.png"}]
         */

        private BigCourseBean bigCourse;
        /**
         * packageId : 202008201946481588
         * courseId : 531847770749244411
         * classId : 558619293577134667
         * courseName : 淘宝无货源训练营
         * courseType : 0
         * currentDay : 0
         * totalDay : 5
         * customerNum : 0
         */

        private List<XbCourseListBean> xbCourseList;

        public BigCourseBean getBigCourse() {
            return bigCourse;
        }

        public void setBigCourse(BigCourseBean bigCourse) {
            this.bigCourse = bigCourse;
        }

        public List<XbCourseListBean> getXbCourseList() {
            return xbCourseList;
        }

        public void setXbCourseList(List<XbCourseListBean> xbCourseList) {
            this.xbCourseList = xbCourseList;
        }

        public static class BigCourseBean {
            private int contractStatus;
            private int selectClassStatus;
            private int addressStatus;
            private int showTeacherStatus;
            private String startTime;
            private String packageName;
            private String className;
            private String teacherQrCode;
            /**
             * courseId : 533305521744614302
             * courseName : 淘宝无货源赚钱技能课
             * studyStatus : 0
             * coverImage : https://image.xicaishe.com/20220111175303cXnn5ERQ.png
             */

            private List<BigCourseListBean> bigCourseList;

            public int getContractStatus() {
                return contractStatus;
            }

            public void setContractStatus(int contractStatus) {
                this.contractStatus = contractStatus;
            }

            public int getSelectClassStatus() {
                return selectClassStatus;
            }

            public void setSelectClassStatus(int selectClassStatus) {
                this.selectClassStatus = selectClassStatus;
            }

            public int getAddressStatus() {
                return addressStatus;
            }

            public void setAddressStatus(int addressStatus) {
                this.addressStatus = addressStatus;
            }

            public int getShowTeacherStatus() {
                return showTeacherStatus;
            }

            public void setShowTeacherStatus(int showTeacherStatus) {
                this.showTeacherStatus = showTeacherStatus;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public String getClassName() {
                return className;
            }

            public void setClassName(String className) {
                this.className = className;
            }

            public String getTeacherQrCode() {
                return teacherQrCode;
            }

            public void setTeacherQrCode(String teacherQrCode) {
                this.teacherQrCode = teacherQrCode;
            }

            public List<BigCourseListBean> getBigCourseList() {
                return bigCourseList;
            }

            public void setBigCourseList(List<BigCourseListBean> bigCourseList) {
                this.bigCourseList = bigCourseList;
            }

            public static class BigCourseListBean {
                private String courseId;
                private String courseName;
                private int studyStatus;
                private String coverImage;

                public String getCourseId() {
                    return courseId;
                }

                public void setCourseId(String courseId) {
                    this.courseId = courseId;
                }

                public String getCourseName() {
                    return courseName;
                }

                public void setCourseName(String courseName) {
                    this.courseName = courseName;
                }

                public int getStudyStatus() {
                    return studyStatus;
                }

                public void setStudyStatus(int studyStatus) {
                    this.studyStatus = studyStatus;
                }

                public String getCoverImage() {
                    return coverImage;
                }

                public void setCoverImage(String coverImage) {
                    this.coverImage = coverImage;
                }
            }
        }

        public static class XbCourseListBean {
            private String packageId;
            private String courseId;
            private String classId;
            private String courseName;
            private int courseType;
            private int currentDay;
            private int totalDay;
            private int customerNum;

            public String getPackageId() {
                return packageId;
            }

            public void setPackageId(String packageId) {
                this.packageId = packageId;
            }

            public String getCourseId() {
                return courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getClassId() {
                return classId;
            }

            public void setClassId(String classId) {
                this.classId = classId;
            }

            public String getCourseName() {
                return courseName;
            }

            public void setCourseName(String courseName) {
                this.courseName = courseName;
            }

            public int getCourseType() {
                return courseType;
            }

            public void setCourseType(int courseType) {
                this.courseType = courseType;
            }

            public int getCurrentDay() {
                return currentDay;
            }

            public void setCurrentDay(int currentDay) {
                this.currentDay = currentDay;
            }

            public int getTotalDay() {
                return totalDay;
            }

            public void setTotalDay(int totalDay) {
                this.totalDay = totalDay;
            }

            public int getCustomerNum() {
                return customerNum;
            }

            public void setCustomerNum(int customerNum) {
                this.customerNum = customerNum;
            }
        }
    }
}
