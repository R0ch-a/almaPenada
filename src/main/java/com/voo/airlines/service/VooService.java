package com.voo.airlines.service;

import com.voo.airlines.model.Destino;
import com.voo.airlines.model.Voo;
import com.voo.airlines.model.VooEconomico;
import com.voo.airlines.model.VooExecutivo;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            "28/08/2025",
            "05/09/2025",
            "12/09/2025",
            "19/09/2025",
            "26/09/2025",
            "03/10/2025",
            "10/10/2025",
            "17/10/2025"
    );

    private final List<String> poltronasEconomicas = Arrays.asList(
        "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10",
        "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10",
        "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10",
        "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10",
        "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "E10"
    );

    private final List<String> poltronasExecutivas = Arrays.asList(
        "Z1", "Z2", "Z3", "Z4", "Z5",
        "W1", "W2", "W3", "W4", "W5"
    );

    private final String arquivoPassagensPath;

    public VooService() {
        this.arquivoPassagensPath = "src/main/resources/dados/passagens.txt";
    }

    public VooService(String arquivoPassagensPath) {
        this.arquivoPassagensPath = arquivoPassagensPath;
    }

    public List<String> getPoltronasPorClasse(String classe) {
        if ("Executiva".equals(classe)) {
            return poltronasExecutivas;
        }
        return poltronasEconomicas;
    }
    
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

    public void salvarPassagem(Voo voo) {
        try {
            File arquivo = new File(arquivoPassagensPath);
            File diretorio = arquivo.getParentFile();
            if (diretorio != null && !diretorio.exists()) {
                diretorio.mkdirs();
                System.out.println("Diretório 'dados' criado em: " + diretorio.getAbsolutePath());
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoPassagensPath, true))) {
                voo.setCodigo(gerarCodigo());
                writer.write(voo.toString());
                writer.newLine();
                System.out.println("Passagem salva: " + voo.toString());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar a passagem: " + e.getMessage());
        }
    }

    public List<Voo> carregarPassagens() {
        List<Voo> passagens = new ArrayList<>();
        File arquivo = new File(arquivoPassagensPath);

        System.out.println("Tentando carregar do arquivo: " + arquivo.getAbsolutePath());

        if (!arquivo.exists()) {
            System.out.println("Arquivo de passagens não encontrado. Iniciando com lista vazia.");
            return passagens;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                // PADRÃO DE REGEX ATUALIZADO
                Pattern padrao = Pattern.compile("Código: (.*?), Origem: (.*?), Destino: (.*?), Data: (.*?), Poltrona: (.*?), Classe: (.*)");
                Matcher matcher = padrao.matcher(linha);
                
                if (matcher.matches()) {
                    String codigo = matcher.group(1);
                    String origem = matcher.group(2);
                    String cidadeDestino = matcher.group(3);
                    String data = matcher.group(4);
                    String poltrona = matcher.group(5);
                    String classe = matcher.group(6);
                    
                    Voo voo;
                    if ("Executiva".equals(classe)) {
                        voo = new VooExecutivo();
                    } else {
                        voo = new VooEconomico();
                    }

                    voo.setCodigo(codigo);
                    voo.setOrigem(origem);
                    voo.setDestino(new Destino(cidadeDestino, "", ""));
                    voo.setData(data);
                    voo.setPoltrona(poltrona);
                    voo.setClasse(classe);
                    passagens.add(voo);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar as passagens: " + e.getMessage());
        }
        System.out.println(passagens.size() + " passagens carregadas com sucesso.");
        return passagens;
    }

    private String gerarCodigo() {
        return UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }
}