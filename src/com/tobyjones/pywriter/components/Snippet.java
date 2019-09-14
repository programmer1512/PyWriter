package com.tobyjones.pywriter.components;

public class Snippet {

    private static String title;
    private static String content;

    public Snippet(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
