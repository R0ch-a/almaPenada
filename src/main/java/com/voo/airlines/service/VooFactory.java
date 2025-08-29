package com.voo.airlines.service;

import com.voo.airlines.model.Voo;
import com.voo.airlines.model.VooEconomico;
import com.voo.airlines.model.VooExecutivo;
import org.springframework.stereotype.Component;

@Component
public class VooFactory {
    public Voo criarVoo(String classe) {
        if ("Executiva".equalsIgnoreCase(classe)) {
            VooExecutivo voo = new VooExecutivo();
            voo.setClasse("Executiva");
            return voo;
        } else {
            VooEconomico voo = new VooEconomico();
            voo.setClasse("Econômica");
            return voo;
        }
    }
}