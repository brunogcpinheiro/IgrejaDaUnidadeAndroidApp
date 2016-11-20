package com.brunogcpinheiro.igrejadaunidade;

import android.widget.Button;
import android.widget.RelativeLayout;

public class Oracao {

    private String autor, oracao;
    private Button deletar;

    public Oracao() {
    }

    public Oracao(String autor, String oracao, Button deletar) {
        this.autor = autor;
        this.oracao = oracao;
        this.deletar = deletar;
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

    public Button getDeletar() {
        return deletar;
    }

    public void setDeletar(Button deletar) {
        this.deletar = deletar;
    }
}