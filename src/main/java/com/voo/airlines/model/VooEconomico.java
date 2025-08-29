package com.voo.airlines.model;

public class VooEconomico extends Voo {

    private final double fatorMultiplicador = 1.0;

    public VooEconomico() {
        super("Econômica");
    }

    @Override
    public double calcularPreco() {
        String cidade = getDestino().getCidade();
        double precoBase;
        
        if ("Curitiba".equals(cidade) || "Rio de Janeiro".equals(cidade)) {
            precoBase = 2000.0;
        } else if ("Recife".equals(cidade)) {
            precoBase = 1400.0;
        } else {
            precoBase = 0.0; // Valor padrão para outros destinos
        }
        
        return precoBase * fatorMultiplicador;
    }
}