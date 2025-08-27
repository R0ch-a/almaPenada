package com.voo.airlines.service;

import com.voo.airlines.model.Destino;
import com.voo.airlines.model.Voo;
import com.voo.airlines.model.VooEconomico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VooServiceTest {

    private VooService vooService;
    private File tempFile;
    
    @BeforeEach
    void setUp(@TempDir Path tempDir) {
        // Cria um arquivo temporário para o teste dentro de um diretório temporário
        this.tempFile = tempDir.resolve("passagens_test.txt").toFile();
        this.vooService = new VooService(tempFile.getAbsolutePath());
    }
    
    @Test
    void testar_listas_fixas() {
        List<Destino> destinos = vooService.getDestinos();
        List<String> datas = vooService.getDatas();
        
        assertFalse(destinos.isEmpty(), "A lista de destinos não deve estar vazia.");
        assertFalse(datas.isEmpty(), "A lista de datas não deve estar vazia.");
        assertEquals(3, destinos.size(), "Deve haver 3 destinos.");
    }
    
    @Test
    void testar_obter_destino_por_sigla() {
        Destino destino = vooService.getDestinoBySigla("GIG");
        assertNotNull(destino, "O destino com a sigla GIG deve ser encontrado.");
        assertEquals("Rio de Janeiro", destino.getCidade(), "A cidade deve ser Rio de Janeiro.");
        
        Destino destinoInvalido = vooService.getDestinoBySigla("XYZ");
        assertNull(destinoInvalido, "Nenhum destino deve ser encontrado para uma sigla inválida.");
    }

    @Test
    void testar_salvar_e_carregar_passagem() {
        Voo voo = new VooEconomico();
        voo.setOrigem("Aracaju");
        voo.setDestino(new Destino("Curitiba", "CWB", "3h 20min"));
        voo.setData("20/08/2025");
        voo.setPoltrona("A1");
        
        vooService.salvarPassagem(voo);
        
        List<Voo> passagensCarregadas = vooService.carregarPassagens();
        
        assertFalse(passagensCarregadas.isEmpty(), "A lista de passagens carregadas não deve estar vazia.");
        assertEquals(1, passagensCarregadas.size(), "Deve haver exatamente uma passagem na lista.");
        
        Voo passagemSalva = passagensCarregadas.get(0);
        
        assertEquals(voo.getOrigem(), passagemSalva.getOrigem(), "A origem deve corresponder.");
        assertEquals(voo.getDestino().getCidade(), passagemSalva.getDestino().getCidade(), "O destino deve corresponder.");
        assertEquals(voo.getData(), passagemSalva.getData(), "A data deve corresponder.");
        assertEquals(voo.getPoltrona(), passagemSalva.getPoltrona(), "A poltrona deve corresponder.");
        assertNotNull(passagemSalva.getCodigo(), "O código da passagem não deve ser nulo.");
    }
}