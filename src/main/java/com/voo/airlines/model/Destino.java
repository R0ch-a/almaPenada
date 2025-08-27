package com.voo.airlines.model;

public class Destino {
    private String cidade;
    private String sigla;
    private String duracao;

    // Construtor padrão necessário para o Jackson (biblioteca de JSON)
    public Destino() {
    }

    public Destino(String cidade, String sigla, String duracao) {
        this.cidade = cidade;
        this.sigla = sigla;
        this.duracao = duracao;
    }

    // Getters e Setters
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getSigla() {
        return sigla;
    }
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    public String getDuracao() {
        return duracao;
    }
    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }
}