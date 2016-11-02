package com.brunogcpinheiro.igrejadaunidade;

public class Celula {

    private String anfitrioes, nome, local, diaehora, telefone;

    public Celula() {
    }

    public Celula(String anfitrioes, String nome, String local, String diaehora, String telefone) {
        this.anfitrioes = anfitrioes;
        this.nome = nome;
        this.local = local;
        this.diaehora = diaehora;
        this.telefone = telefone;
    }

    public String getAnfitrioes() {
        return anfitrioes;
    }

    public void setAnfitrioes(String anfitrioes) {
        this.anfitrioes = anfitrioes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDiaehora() {
        return diaehora;
    }

    public void setDiaehora(String diaehora) {
        this.diaehora = diaehora;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
