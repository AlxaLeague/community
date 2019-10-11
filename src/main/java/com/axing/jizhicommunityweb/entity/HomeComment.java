package com.axing.jizhicommunityweb.entity;

public class HomeComment {
    private String title;
    private String thesisid;
    private String commentContent;
    private String timeStamp;

    public HomeComment() {
    }

    public HomeComment(String title, String thesisid, String commentContent, String timeStamp) {
        this.title = title;
        this.thesisid = thesisid;
        this.commentContent = commentContent;
        this.timeStamp = timeStamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getThesisid() {
        return thesisid;
    }

    public void setThesisid(String thesisid) {
        this.thesisid = thesisid;
    }

    @Override
    public String toString() {
        return "HomeComment{" +
                "title='" + title + '\'' +
                ", thesisid='" + thesisid + '\'' +
                ", commentContent='" + commentContent + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                '}';
    }
}
