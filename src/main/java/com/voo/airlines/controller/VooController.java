package com.voo.airlines.controller;

import com.voo.airlines.model.Voo;
import com.voo.airlines.service.VooService;
import com.voo.airlines.model.Destino;
import com.voo.airlines.service.VooFactory;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.List;

@Controller
@SessionAttributes("voo")
public class VooController {

    private final VooService vooService;
    private final VooFactory vooFactory;

    public VooController(VooService vooService, VooFactory vooFactory) {
        this.vooService = vooService;
        this.vooFactory = vooFactory;
    }

    @ModelAttribute("voo")
    public Voo setupVoo() {
        return vooFactory.criarVoo("Econômica");
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("destinos", vooService.getDestinos());
        model.addAttribute("datas", vooService.getDatas());
        List<Voo> passagensEmitidas = vooService.carregarPassagens();
        model.addAttribute("passagensEmitidas", passagensEmitidas);
        return "index";
    }

    @PostMapping("/selecionarDestino")
    public String selecionarDestino(@ModelAttribute Voo voo, @RequestParam String siglaDestino) {
        voo.setDestino(vooService.getDestinoBySigla(siglaDestino));
        return "redirect:/";
    }

    @PostMapping("/selecionarData")
    public String selecionarData(@ModelAttribute Voo voo, @RequestParam String data) {
        voo.setData(data);
        return "redirect:/";
    }

    @PostMapping("/selecionarClasse")
    public String selecionarClasse(HttpSession session, @RequestParam String classe) {
        Voo vooAtual = (Voo) session.getAttribute("voo");
        Voo novoVoo = vooFactory.criarVoo(classe);

        if (vooAtual != null) {
            novoVoo.setOrigem(vooAtual.getOrigem());
            novoVoo.setDestino(vooAtual.getDestino());
            novoVoo.setData(vooAtual.getData());
            novoVoo.setPoltrona(vooAtual.getPoltrona());
        }

        session.setAttribute("voo", novoVoo);
        return "redirect:/";
    }

    @PostMapping("/selecionarPoltrona")
    public String selecionarPoltrona(@ModelAttribute Voo voo, @RequestParam String poltrona) {
        voo.setPoltrona(poltrona);
        return "redirect:/";
    }

    @PostMapping("/emitirPassagem")
    public String emitirPassagem(@ModelAttribute Voo voo, Model model, SessionStatus sessionStatus) {
        // Validação: verifica se todas as opções essenciais foram selecionadas
        if (voo.getDestino() == null || voo.getData() == null || voo.getPoltrona() == null) {
            model.addAttribute("error", "Por favor, selecione todas as opções para emitir a passagem.");
            
            // Adiciona as listas de destinos e datas ao modelo para a renderização da página
            model.addAttribute("destinos", vooService.getDestinos());
            model.addAttribute("datas", vooService.getDatas());
            
            // Retorna para a página inicial com a mensagem de erro
            return "index";
        }
        
        // Se a validação passar, continua com a lógica de negócio
        voo.setOrigem("Aracaju");
        voo.setHorario("08:00");
        vooService.salvarPassagem(voo);
        
        // Finaliza a sessão da passagem
        sessionStatus.setComplete();
        
        // Redireciona para a página de confirmação
        model.addAttribute("voo", voo);
        return "passagem-confirmacao";
    }
}