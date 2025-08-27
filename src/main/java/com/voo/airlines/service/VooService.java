package com.voo.airlines.service;

import com.voo.airlines.model.Destino;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class VooService {

    private final List<Destino> destinosFixos = Arrays.asList(
            new Destino("Recife", "REC", "2h 30min"),
            new Destino("Rio de Janeiro", "GIG", "1h 10min"),
            new Destino("Curitiba", "CWB", "3h 20min")
    );

    private final List<String> datasFixas = Arrays.asList(
            "16/08/2025",
            "20/08/2025",
            "28/08/2025"
    );

    private static final double PRECO_BASE = 500.0;
    private static final double MULTIPLICADOR_EXECUTIVA = 1.5;

    public List<Destino> getDestinos() {
        return destinosFixos;
    }

    public List<String> getDatas() {
        return datasFixas;
    }

    public Destino getDestinoBySigla(String sigla) {
        return destinosFixos.stream()
                .filter(d -> d.getSigla().equals(sigla))
                .findFirst()
                .orElse(null);
    }

    public double calcularPreco(String classe) {
        if ("Executiva".equalsIgnoreCase(classe)) {
            return PRECO_BASE * MULTIPLICADOR_EXECUTIVA;
        }
        return PRECO_BASE;
    }
}