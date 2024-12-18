package com.example.firebasemp;

public class Diary {
    private String text;
    private String date;

    // Constructor
    public Diary(String text, String date) {
        this.text = text;
        this.date = date;
    }

    // Getter untuk text
    public String getText() {
        return text;
    }

    // Setter untuk text
    public void setText(String text) {
        this.text = text;
    }

    // Getter untuk date
    public String getDate() {
        return date;
    }

    // Setter untuk date
    public void setDate(String date) {
        this.date = date;
    }
}
