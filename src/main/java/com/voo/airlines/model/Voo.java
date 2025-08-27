package com.voo.airlines.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Voo {

    private String origem;
    private Destino destino;
    private String data;
    private String horario;
    private String duracao;
    private String poltrona;
    
    // Construtor padrão (sem argumentos)
    public Voo() {}

    // Construtor para inicialização de dados
    public Voo(String origem, Destino destino, String data, String horario, String duracao, String poltrona) {
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.horario = horario;
        this.duracao = duracao;
        this.poltrona = poltrona;
    }

    // Método abstrato para calcular o preço, que será implementado pelas subclasses
    public abstract double getPreco();
    
    // Método abstrato para obter o nome da classe, que será implementado pelas subclasses
    public abstract String getClasse();

    // Getters e Setters
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
}