package com.brunogcpinheiro.igrejadaunidade;

public class Versiculo {

    private String date, ref, verse;

    public Versiculo() {
    }

    public Versiculo(String date, String ref, String verse) {
        this.date = date;
        this.ref = ref;
        this.verse = verse;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }
}