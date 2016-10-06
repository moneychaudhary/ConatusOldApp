package com.example.money.conatusapp.Home;

/**
 * Created by #money on 10/2/2016.
 */

public class Post {
    private String title;
    private String date;
    private String time;
    private String subhead;
    private String image;
    private String desc;

    public Post() {

    }

    public Post(String title, String desc, String image, String subhead, String time, String date) {
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.subhead = subhead;
        this.time = time;
        this.date = date;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
