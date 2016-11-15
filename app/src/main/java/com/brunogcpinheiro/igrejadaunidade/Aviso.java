package com.brunogcpinheiro.igrejadaunidade;

/**
 * Created by Bruno on 08/10/2016.
 */

public class Aviso {

    private String date, aviso;

    public Aviso() {
    }

    public Aviso(String date, String aviso) {
        this.date = date;
        this.aviso = aviso;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAviso() {
        return aviso;
    }

    public void setAviso(String aviso) {
        this.aviso = aviso;
    }

}
