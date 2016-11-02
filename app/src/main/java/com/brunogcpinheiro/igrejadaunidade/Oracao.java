package com.brunogcpinheiro.igrejadaunidade;

public class Oracao {

    private String autor, oracao;

    public Oracao() {
    }

    public Oracao(String autor, String oracao) {
        this.autor = autor;
        this.oracao = oracao;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getOracao() {
        return oracao;
    }

    public void setOracao(String oracao) {
        this.oracao = oracao;
    }
}