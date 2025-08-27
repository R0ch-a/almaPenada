package com.voo.airlines.controller;

import com.voo.airlines.model.Voo;
import com.voo.airlines.service.VooService;
import com.voo.airlines.model.VooEconomico;
import com.voo.airlines.model.VooExecutivo;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("voo")
public class VooController {

    private final VooService vooService;

    public VooController(VooService vooService) {
        this.vooService = vooService;
    }

    @ModelAttribute("voo")
    public Voo setupVoo() {
        return new VooEconomico(); // Começa com um objeto padrão para a sessão
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("destinos", vooService.getDestinos());
        model.addAttribute("datas", vooService.getDatas());
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

        // Copia os dados existentes para a nova instância
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
        model.addAttribute("voo", voo);
        sessionStatus.setComplete();
        return "passagem-confirmacao";
    }
}