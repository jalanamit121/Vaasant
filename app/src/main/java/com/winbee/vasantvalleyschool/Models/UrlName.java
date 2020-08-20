package com.winbee.vasantvalleyschool.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UrlName implements Serializable {

    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Topic")
    @Expose
    private String topic;
    @SerializedName("Faculty")
    @Expose
    private String faculty;
    @SerializedName("Designation")
    @Expose
    private String designation;
    @SerializedName("Published")
    @Expose
    private String published;
    @SerializedName("Additional_info")
    @Expose
    private String additional_info;
    @SerializedName("DocumentId")
    @Expose
    private String documentId;
    @SerializedName("Thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("Type")
    @Expose
    private String type;

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}