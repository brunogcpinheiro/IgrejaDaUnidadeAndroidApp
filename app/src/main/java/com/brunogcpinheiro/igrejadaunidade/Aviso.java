package com.brunogcpinheiro.igrejadaunidade;

/**
 * Created by Bruno on 08/10/2016.
 */

public class Aviso {

    private String date, aviso, aviso2, aviso3, aviso4, aviso5;

    public Aviso() {
    }

    public Aviso(String date, String aviso, String aviso2, String aviso3, String aviso4, String aviso5) {
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

    public String getAviso2() {
        return aviso2;
    }

    public void setAviso2(String aviso2) {
        this.aviso2 = aviso2;
    }

    public String getAviso3() {
        return aviso3;
    }

    public void setAviso3(String aviso3) {
        this.aviso3 = aviso3;
    }

    public String getAviso4() {
        return aviso4;
    }

    public void setAviso4(String aviso4) {
        this.aviso4 = aviso4;
    }

    public String getAviso5() {
        return aviso5;
    }

    public void setAviso5(String aviso5) {
        this.aviso5 = aviso5;
    }
}
