package com.brunogcpinheiro.igrejadaunidade;

/**
 * Created by Bruno on 15/11/2016.
 */

public class Jejum {

    private String motivos, inicio, termino;

    public Jejum() {
    }

    public Jejum(String motivos, String inicio, String termino) {
        this.motivos = motivos;
        this.inicio = inicio;
        this.termino = termino;
    }

    public String getMotivos() {
        return motivos;
    }

    public void setMotivos(String motivos) {
        this.motivos = motivos;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }
}
