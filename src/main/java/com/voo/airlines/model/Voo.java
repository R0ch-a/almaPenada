package com.voo.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Voo {

    private String codigo;
    private String origem;
    private Destino destino;
    private String data;
    private String horario;
    private String duracao;
    private String poltrona;

    public Voo() {}

    public Voo(String origem, Destino destino, String data, String horario, String duracao, String poltrona) {
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.horario = horario;
        this.duracao = duracao;
        this.poltrona = poltrona;
    }

    public abstract double getPreco();
    public abstract String getClasse();
    
    // Getters e Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getOrigem() { return origem; }
    public void setOrigem(String origem) { this.origem = origem; }
    public Destino getDestino() { return destino; }
    public void setDestino(Destino destino) { this.destino = destino; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public String getDuracao() { return duracao; }
    public void setDuracao(String duracao) { this.duracao = duracao; }
    public String getPoltrona() { return poltrona; }
    public void setPoltrona(String poltrona) { this.poltrona = poltrona; }

    @Override
    public String toString() {
        return String.format("Código: %s, Origem: %s, Destino: %s, Data: %s, Poltrona: %s",
                this.codigo != null ? this.codigo : "N/A",
                this.origem != null ? this.origem : "N/A",
                this.destino != null ? this.destino.getCidade() : "N/A",
                this.data != null ? this.data : "N/A",
                this.poltrona != null ? this.poltrona : "N/A"
        );
    }
}