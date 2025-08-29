package com.voo.airlines.model;

//import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Voo {

    private String codigo;
    private String origem;
    private Destino destino;
    private String data;
    private String horario;
    private String poltrona;
    private String classe;
    private String portaoEmbarque;

    public Voo() {}

    public Voo(String origem, Destino destino, String data, String horario, String duracao, String poltrona) {
        this.origem = origem;
        this.destino = destino;
        this.data = data;
        this.horario = horario;
        this.poltrona = poltrona;

    }

    public abstract double getPreco();
    
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
    public String getPoltrona() { return poltrona; }
    public void setPoltrona(String poltrona) { this.poltrona = poltrona; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public String getPortaoEmbarque() { return portaoEmbarque; }
    public void setPortaoEmbarque(String portaoEmbarque) { this.portaoEmbarque = portaoEmbarque; }

    @Override
    public String toString() {
        return String.format("Código: %s, Origem: %s, Destino: %s, Data: %s, Horário: %s, Portão de Embarque: %s, Poltrona: %s, Classe: %s",
                codigo, origem, destino.getCidade(), data, horario, portaoEmbarque, poltrona, classe);
    }
}