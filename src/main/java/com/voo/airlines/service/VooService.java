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
            "28/08/2025"
    );

    private final String arquivoPassagensPath;

    // Construtor padrão (usado pela aplicação em produção)
    public VooService() {
        this.arquivoPassagensPath = "src/main/resources/dados/passagens.txt";
    }

    // Construtor para testes, permitindo a injeção do caminho do arquivo
    public VooService(String arquivoPassagensPath) {
        this.arquivoPassagensPath = arquivoPassagensPath;
    }

    // O restante dos métodos permanece o mesmo
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoPassagensPath, true))) {
            voo.setCodigo(gerarCodigo());
            writer.write(voo.toString());
            writer.newLine();
            System.out.println("Passagem salva: " + voo.toString());
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
                Pattern padrao = Pattern.compile("Código: (.*?), Origem: (.*?), Destino: (.*?), Data: (.*?), Poltrona: (.*)");
                Matcher matcher = padrao.matcher(linha);
                
                if (matcher.matches()) {
                    String codigo = matcher.group(1);
                    String origem = matcher.group(2);
                    String cidadeDestino = matcher.group(3);
                    String data = matcher.group(4);
                    String poltrona = matcher.group(5);

                    Voo voo = new VooEconomico();
                    voo.setCodigo(codigo);
                    voo.setOrigem(origem);
                    voo.setDestino(new Destino(cidadeDestino, "", ""));
                    voo.setData(data);
                    voo.setPoltrona(poltrona);

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