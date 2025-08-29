package com.voo.airlines.service;

import com.voo.airlines.model.Destino;
import com.voo.airlines.model.Voo; // Importa Voo
import com.voo.airlines.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Classe de teste para VooService usando JUnit 5 e Mockito.
 * Esta classe testa a funcionalidade da VooService de forma isolada.
 */
@ExtendWith(MockitoExtension.class)
public class VooServiceTest {

    // @TempDir cria um diretório temporário para a execução dos testes
    @TempDir
    Path tempDir;

    private VooService vooService;
    private Path tempFilePath;

    /**
     * O método 'setUp' é executado antes de cada teste.
     * Ele inicializa a VooService com um caminho de arquivo temporário
     * para garantir que os testes não interfiram no arquivo de produção.
     */
    @BeforeEach
    public void setUp() {
        tempFilePath = tempDir.resolve("passagens.txt");
        // Inicializa o serviço com o construtor que aceita o caminho do arquivo
        vooService = new VooService(tempFilePath.toString());
    }

    /**
     * Testa se o método getPoltronasPorClasse retorna a lista correta
     * para a classe "Econômica".
     */
    @Test
    public void testGetPoltronasPorClasse_Economica() {
        List<String> poltronas = vooService.getPoltronasPorClasse("Econômica");
        // Verifica se a lista não está vazia e se contém uma poltrona esperada
        Assertions.assertNotNull(poltronas);
        Assertions.assertEquals(50, poltronas.size());
        Assertions.assertTrue(poltronas.contains("A1"));
    }

    /**
     * Testa se o método getPoltronasPorClasse retorna a lista correta
     * para a classe "Executiva".
     */
    @Test
    public void testGetPoltronasPorClasse_Executiva() {
        List<String> poltronas = vooService.getPoltronasPorClasse("Executiva");
        // Verifica se a lista não está vazia e se contém uma poltrona esperada
        Assertions.assertNotNull(poltronas);
        Assertions.assertEquals(10, poltronas.size());
        Assertions.assertTrue(poltronas.contains("Z1"));
    }

    /**
     * Testa se o método salvarPassagem salva a passagem no arquivo.
     * Este teste é mais complexo, pois envolve a criação e verificação do arquivo.
     */
    @Test
    public void testSalvarEcarregarPassagem() throws IOException {
        // Crie uma passagem de exemplo (VooExecutivo para testar a herança)
        VooExecutivo voo = new VooExecutivo();
        voo.setOrigem("Aracaju");
        voo.setDestino(new Destino("Rio de Janeiro", "GIG", "1h 10min", "14:30"));
        voo.setData("20/08/2025");
        voo.setPoltrona("W2");

        // Salva a passagem
        vooService.salvarPassagem(voo);

        // Carrega as passagens do arquivo. A variável deve ser do tipo List<Voo>.
        List<Voo> passagensSalvas = vooService.carregarPassagens();

        // Verifica se exatamente 1 passagem foi carregada
        Assertions.assertEquals(1, passagensSalvas.size());

        // Pega a passagem carregada. Faz um cast para VooEconomico.
        Voo passagemCarregada = passagensSalvas.get(0);

        // Verifica os valores da passagem carregada
        Assertions.assertEquals("Aracaju", passagemCarregada.getOrigem());
        Assertions.assertEquals("Rio de Janeiro", passagemCarregada.getDestino().getCidade());
        Assertions.assertEquals("20/08/2025", passagemCarregada.getData());
        Assertions.assertEquals("W2", passagemCarregada.getPoltrona());
        Assertions.assertEquals("Executiva", passagemCarregada.getClasse());
        Assertions.assertNotNull(passagemCarregada.getCodigo());
    }

    /**
     * Testa se o método getDestinoBySigla retorna o destino correto
     * para uma sigla válida.
     */
    @Test
    public void testGetDestinoBySigla_Existente() {
        Destino destino = vooService.getDestinoBySigla("CWB");
        Assertions.assertNotNull(destino);
        Assertions.assertEquals("Curitiba", destino.getCidade());
    }

    /**
     * Testa se o método getDestinoBySigla retorna 'null'
     * para uma sigla que não existe.
     */
    @Test
    public void testGetDestinoBySigla_NaoExistente() {
        Destino destino = vooService.getDestinoBySigla("XYZ");
        Assertions.assertNull(destino);
    }
}
