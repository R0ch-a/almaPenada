package com.voo.airlines.model;

public class VooEconomico extends Voo {
    
    private static final double PRECO_BASE = 2500.0;

    @Override
    public double getPreco() {
        return PRECO_BASE;
    }
}