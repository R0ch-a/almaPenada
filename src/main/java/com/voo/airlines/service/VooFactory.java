package com.voo.airlines.service;

import com.voo.airlines.model.Voo;
import com.voo.airlines.model.VooEconomico;
import com.voo.airlines.model.VooExecutivo;
import org.springframework.stereotype.Component;

@Component
public class VooFactory {
    public Voo criarVoo(String classe) {
        if ("Executiva".equals(classe)) {
            return new VooExecutivo();
        } else {
            return new VooEconomico();
        }
    }
}