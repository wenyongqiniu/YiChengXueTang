package com.example.yichengxuetang.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShowCourseListResponse implements Serializable {

    /**
     * code : 0
     * message : 
     * data : {"xbCourseList":[{"packageId":"202008201946481588","courseId":"531847770749244411","classId":"558619293577134667","courseName":"淘宝无货源训练营","courseType":0,"currentDay":0,"totalDay":5,"customerNum":0}],"bigCourse":{"packageId":"533305006155600543","contractStatus":1,"selectClassStatus":1,"addressStatus":1,"showTeacherStatus":1,"startTime":"2022-04-07","packageName":"淘宝无货源赚钱技能课","className":"19期1班","teacherQrCode":"https://image.xicaishe.com/20220316175034pZJMbdNH.jpg","bigCourseList":[{"courseId":"533305521744614302","courseName":"淘宝无货源赚钱技能课","studyStatus":0,"coverImage":"https://image.xicaishe.com/20220111175303cXnn5ERQ.png"}]}}
     */

    private Integer code;
    private String message;
    /**
     * xbCourseList : [{"packageId":"202008201946481588","courseId":"531847770749244411","classId":"558619293577134667","courseName":"淘宝无货源训练营","courseType":0,"currentDay":0,"totalDay":5,"customerNum":0}]
     * bigCourse : {"packageId":"533305006155600543","contractStatus":1,"selectClassStatus":1,"addressStatus":1,"showTeacherStatus":1,"startTime":"2022-04-07","packageName":"淘宝无货源赚钱技能课","className":"19期1班","teacherQrCode":"https://image.xicaishe.com/20220316175034pZJMbdNH.jpg","bigCourseList":[{"courseId":"533305521744614302","courseName":"淘宝无货源赚钱技能课","studyStatus":0,"coverImage":"https://image.xicaishe.com/20220111175303cXnn5ERQ.png"}]}
     */

    private DataBean data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public static class DataBean implements Serializable{
        /**
         * packageId : 533305006155600543
         * contractStatus : 1
         * selectClassStatus : 1
         * addressStatus : 1
         * showTeacherStatus : 1
         * startTime : 2022-04-07
         * packageName : 淘宝无货源赚钱技能课
         * className : 19期1班
         * teacherQrCode : https://image.xicaishe.com/20220316175034pZJMbdNH.jpg
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

        public static class BigCourseBean implements Serializable{
            private String packageId;
            private Integer contractStatus;
            private Integer selectClassStatus;
            private Integer addressStatus;
            private Integer showTeacherStatus;
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

            public String getPackageId() {
                return packageId;
            }

            public void setPackageId(String packageId) {
                this.packageId = packageId;
            }

            public Integer getContractStatus() {
                return contractStatus;
            }

            public void setContractStatus(Integer contractStatus) {
                this.contractStatus = contractStatus;
            }

            public Integer getSelectClassStatus() {
                return selectClassStatus;
            }

            public void setSelectClassStatus(Integer selectClassStatus) {
                this.selectClassStatus = selectClassStatus;
            }

            public Integer getAddressStatus() {
                return addressStatus;
            }

            public void setAddressStatus(Integer addressStatus) {
                this.addressStatus = addressStatus;
            }

            public Integer getShowTeacherStatus() {
                return showTeacherStatus;
            }

            public void setShowTeacherStatus(Integer showTeacherStatus) {
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

            public static class BigCourseListBean implements Serializable{
                private String courseId;
                private String courseName;
                private Integer studyStatus;
                private Integer contentType;
                private String coverImage;

                public Integer getContentType() {
                    return contentType;
                }

                public void setContentType(Integer contentType) {
                    this.contentType = contentType;
                }

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

                public Integer getStudyStatus() {
                    return studyStatus;
                }

                public void setStudyStatus(Integer studyStatus) {
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
            private Integer courseType;
            private Integer currentDay;
            private Integer totalDay;
            private Integer customerNum;
            private ArrayList<String> imgUrls;

            public ArrayList<String> getImgUrls() {
                return imgUrls;
            }

            public void setImgUrls(ArrayList<String> imgUrls) {
                this.imgUrls = imgUrls;
            }

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

            public Integer getCourseType() {
                return courseType;
            }

            public void setCourseType(Integer courseType) {
                this.courseType = courseType;
            }

            public Integer getCurrentDay() {
                return currentDay;
            }

            public void setCurrentDay(Integer currentDay) {
                this.currentDay = currentDay;
            }

            public Integer getTotalDay() {
                return totalDay;
            }

            public void setTotalDay(Integer totalDay) {
                this.totalDay = totalDay;
            }

            public Integer getCustomerNum() {
                return customerNum;
            }

            public void setCustomerNum(Integer customerNum) {
                this.customerNum = customerNum;
            }
            
        }
    }
    
}
