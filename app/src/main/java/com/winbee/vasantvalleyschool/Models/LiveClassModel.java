package com.winbee.vasantvalleyschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiveClassModel implements Serializable {
        @SerializedName("ContentLink")
        @Expose
        private String contentLink;
        @SerializedName("ParentBucketId")
        @Expose
        private String parentBucketId;
        @SerializedName("CT_type_code")
        @Expose
        private Integer cT_type_code;
        @SerializedName("Teacher")
        @Expose
        private String teacher;
        @SerializedName("Subject")
        @Expose
        private String subject;
        @SerializedName("Topic")
        @Expose
        private String topic;
        @SerializedName("Content_Info")
        @Expose
        private String content_Info;
        @SerializedName("Uploaded_by")
        @Expose
        private String uploaded_by;
        @SerializedName("DT_type_name")
        @Expose
        private String dT_type_name;
        @SerializedName("DT_type_code")
        @Expose
        private Integer dT_type_code;
        @SerializedName("RS_type_name")
        @Expose
        private String rS_type_name;
        @SerializedName("RS_type_code")
        @Expose
        private Integer rS_type_code;
        @SerializedName("CS_type_name")
        @Expose
        private String cS_type_name;
        @SerializedName("CS_type_code")
        @Expose
        private Integer cS_type_code;
        @SerializedName("Live_Date")
        @Expose
        private String live_Date;
        @SerializedName("Start_Time")
        @Expose
        private String start_Time;
        @SerializedName("Audience")
        @Expose
        private String audience;
        @SerializedName("LiveClassId")
        @Expose
        private String liveClassId;
        @SerializedName("IsClassToday")
        @Expose
        private Integer isClassToday;

        public String getContentLink() {
            return contentLink;
        }

        public void setContentLink(String contentLink) {
            this.contentLink = contentLink;
        }

        public String getParentBucketId() {
            return parentBucketId;
        }

        public void setParentBucketId(String parentBucketId) {
            this.parentBucketId = parentBucketId;
        }

        public Integer getCT_type_code() {
            return cT_type_code;
        }

        public void setCT_type_code(Integer cT_type_code) {
            this.cT_type_code = cT_type_code;
        }

        public String getTeacher() {
            return teacher;
        }

        public void setTeacher(String teacher) {
            this.teacher = teacher;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getContent_Info() {
            return content_Info;
        }

        public void setContent_Info(String content_Info) {
            this.content_Info = content_Info;
        }

        public String getUploaded_by() {
            return uploaded_by;
        }

        public void setUploaded_by(String uploaded_by) {
            this.uploaded_by = uploaded_by;
        }

        public String getDT_type_name() {
            return dT_type_name;
        }

        public void setDT_type_name(String dT_type_name) {
            this.dT_type_name = dT_type_name;
        }

        public Integer getDT_type_code() {
            return dT_type_code;
        }

        public void setDT_type_code(Integer dT_type_code) {
            this.dT_type_code = dT_type_code;
        }

        public String getRS_type_name() {
            return rS_type_name;
        }

        public void setRS_type_name(String rS_type_name) {
            this.rS_type_name = rS_type_name;
        }

        public Integer getRS_type_code() {
            return rS_type_code;
        }

        public void setRS_type_code(Integer rS_type_code) {
            this.rS_type_code = rS_type_code;
        }

        public String getCS_type_name() {
            return cS_type_name;
        }

        public void setCS_type_name(String cS_type_name) {
            this.cS_type_name = cS_type_name;
        }

        public Integer getCS_type_code() {
            return cS_type_code;
        }

        public void setCS_type_code(Integer cS_type_code) {
            this.cS_type_code = cS_type_code;
        }

        public String getLive_Date() {
            return live_Date;
        }

        public void setLive_Date(String live_Date) {
            this.live_Date = live_Date;
        }

        public String getStart_Time() {
            return start_Time;
        }

        public void setStart_Time(String start_Time) {
            this.start_Time = start_Time;
        }

        public String getAudience() {
            return audience;
        }

        public void setAudience(String audience) {
            this.audience = audience;
        }

        public String getLiveClassId() {
            return liveClassId;
        }

        public void setLiveClassId(String liveClassId) {
            this.liveClassId = liveClassId;
        }

        public Integer getIsClassToday() {
            return isClassToday;
        }

        public void setIsClassToday(Integer isClassToday) {
            this.isClassToday = isClassToday;
        }

    }