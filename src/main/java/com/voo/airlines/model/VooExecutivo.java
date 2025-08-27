package com.voo.airlines.model;

public class VooExecutivo extends Voo {
    
    private static final double PRECO_BASE = 500.0;
    private static final double MULTIPLICADOR = 1.5;

    @Override
    public double getPreco() {
        return PRECO_BASE * MULTIPLICADOR;
    }
    
    @Override
    public String getClasse() {
        return "Executiva";
    }
}