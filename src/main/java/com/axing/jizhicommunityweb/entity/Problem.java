package com.axing.jizhicommunityweb.entity;

public class Problem {
    String title;

    public Problem() {
    }

    public Problem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Problem{" +
                "title='" + title + '\'' +
                '}';
    }
}
