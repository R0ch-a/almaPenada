package com.voo.airlines.controller;

import com.voo.airlines.model.Voo;
import com.voo.airlines.service.VooService;
import com.voo.airlines.service.VooFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Random;

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
    public String index(Model model, @ModelAttribute Voo voo) {
        model.addAttribute("destinos", vooService.getDestinos());
        model.addAttribute("datas", vooService.getDatas());
        model.addAttribute("poltronas", vooService.getPoltronasPorClasse(voo.getClasse()));
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
    public String selecionarClasse(@ModelAttribute("voo") Voo vooAtual, @RequestParam String classe, HttpSession session) {
        Voo novoVoo = vooFactory.criarVoo(classe);
        novoVoo.setOrigem(vooAtual.getOrigem());
        novoVoo.setDestino(vooAtual.getDestino());
        novoVoo.setData(vooAtual.getData());
        novoVoo.setPoltrona(vooAtual.getPoltrona());
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
        if (voo.getDestino() == null || voo.getData() == null || voo.getPoltrona() == null) {
            model.addAttribute("error", "Por favor, selecione todas as opções para emitir a passagem.");
            model.addAttribute("destinos", vooService.getDestinos());
            model.addAttribute("datas", vooService.getDatas());
            model.addAttribute("poltronas", vooService.getPoltronasPorClasse(voo.getClasse()));
            return "index";
        }
        
        // Adicionando o horário do destino e o portão de embarque
        voo.setOrigem("Aracaju");
        voo.setHorario(voo.getDestino().getHorario()); // Usa o horário do destino
        voo.setPortaoEmbarque("B" + new Random().nextInt(10)); // Portão aleatório de B0 a B9
        
        vooService.salvarPassagem(voo);
        
        sessionStatus.setComplete();
        
        model.addAttribute("voo", voo);
        return "passagem-confirmacao";
    }
}