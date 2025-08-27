package com.voo.airlines.controller;

import com.voo.airlines.model.*;
import com.voo.airlines.service.VooService;
import com.voo.airlines.model.VooEconomico;
import com.voo.airlines.model.VooExecutivo;
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

    public VooController(VooService vooService) {
        this.vooService = vooService;
    }

    @ModelAttribute("voo")
    public Voo setupVoo() {
        return new VooEconomico();
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
        Voo novoVoo;

        if ("Executiva".equalsIgnoreCase(classe)) {
            novoVoo = new VooExecutivo();
        } else {
            novoVoo = new VooEconomico();
        }

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
        voo.setOrigem("Aracaju");
        voo.setHorario("08:00");
        vooService.salvarPassagem(voo); // Salva a passagem antes de redirecionar
        model.addAttribute("voo", voo);
        sessionStatus.setComplete();
        return "passagem-confirmacao";
    }
}