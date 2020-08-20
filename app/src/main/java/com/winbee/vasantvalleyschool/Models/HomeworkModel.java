package com.winbee.vasantvalleyschool.Models;

public class HomeworkModel {
    String title , subject , date ,pdfUrl;

    public HomeworkModel() {
    }

    public HomeworkModel(String title, String subject, String date, String pdfUrl) {
        this.title = title;
        this.subject = subject;
        this.date = date;
        this.pdfUrl = pdfUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    @Override
    public String toString() {
        return "HomeworkModel{" +
                "title='" + title + '\'' +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
