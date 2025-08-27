package com.voo.airlines.controller;

import com.voo.airlines.model.Voo;
import com.voo.airlines.service.VooService;
import com.voo.airlines.model.Destino;
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
        return new Voo();
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
        System.out.println("Destino selecionado: " + voo.getDestino().getSigla());
        return "redirect:/";
    }

    @PostMapping("/selecionarData")
    public String selecionarData(@ModelAttribute Voo voo, @RequestParam String data) {
        voo.setData(data);
        System.out.println("Data selecionada: " + voo.getData());
        return "redirect:/";
    }

    @PostMapping("/selecionarClasse")
    public String selecionarClasse(@ModelAttribute Voo voo, @RequestParam String classe) {
        voo.setClasse(classe);
        voo.setPreco(vooService.calcularPreco(classe));
        System.out.println("Classe selecionada: " + voo.getClasse() + " - Preço: R$" + voo.getPreco());
        return "redirect:/";
    }

    @PostMapping("/selecionarPoltrona")
    public String selecionarPoltrona(@ModelAttribute Voo voo, @RequestParam String poltrona) {
        voo.setPoltrona(poltrona);
        System.out.println("Poltrona selecionada: " + voo.getPoltrona());
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