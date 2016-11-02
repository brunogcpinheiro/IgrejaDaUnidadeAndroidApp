package com.brunogcpinheiro.igrejadaunidade;

public class Evento {

    private String time, title, desc, image, date;

    public Evento(){

    }

    public Evento(String time, String title, String desc, String image, String date) {
        this.time = time;
        this.title = title;
        this.desc = desc;
        this.image = image;
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}