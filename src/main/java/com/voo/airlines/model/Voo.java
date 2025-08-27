package com.voo.airlines.model;

public class Voo {

    private String origem;
    private Destino destino;
    private String data;
    private String horario;
    private String duracao;
    private String classe;
    private String poltrona;
    private double preco;

    // Construtor padrão (sem argumentos) adicionado para o Spring Boot
    public Voo() {
    }

    // Construtor completo
    public Voo(String origem, Destino destino, String data, String horario, String duracao, String classe, String poltrona, double preco) {
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.horario = horario;
        this.duracao = duracao;
        this.classe = classe;
        this.poltrona = poltrona;
        this.preco = preco;
    }

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
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public String getPoltrona() { return poltrona; }
    public void setPoltrona(String poltrona) { this.poltrona = poltrona; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}