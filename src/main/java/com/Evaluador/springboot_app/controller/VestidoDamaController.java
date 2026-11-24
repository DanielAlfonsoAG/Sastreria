package com.Evaluador.springboot_app.controller;

import com.Evaluador.springboot_app.model.VestidoDama;
import com.Evaluador.springboot_app.service.Controladora;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class VestidoDamaController {

    @Autowired
    private Controladora control;

    @GetMapping("/verVestidosDama")
    public String ListaDeVestidos(HttpServletRequest request) {
        List<VestidoDama> listaVestidoDama = control.listarVestidoDama();
        HttpSession session = request.getSession();
        session.setAttribute("listaVestidoDama", listaVestidoDama);
        return "verVestidosDama";
    }

    @PostMapping("/eliminarVestidoDama")
    public String eliminarVestidoDama(@RequestParam("id") String id,
                                  HttpServletRequest request) {
        int id_vestidoDama = Integer.parseInt(id);
        control.borrarVestidoDama(id_vestidoDama);
        return "principal";
    }

    @GetMapping("/editarVestidoDama")
    public String IngresarEditarVestidoDama(@RequestParam("id") long id,
                                        HttpServletRequest request, Model model) {

        Optional<VestidoDama> optionalVestidoDama = control.traerVestidoDama(id);

        if (optionalVestidoDama.isPresent()) {

            HttpSession misession = request.getSession();

            VestidoDama vestidoDama = optionalVestidoDama.get();
            model.addAttribute("vestidoDamaEditar", vestidoDama);
            request.getSession().setAttribute("vestidoDamaEditar", vestidoDama);

            if (misession.getAttribute("usuario") == null) {
                return "redirect:/login";
            }
            return "editarVestidoDama";

        } else {
            // Redirige si el usuario no existe
            return "redirect:/verVestidosDama";
        }
    }
    @GetMapping("/crearVestidoDama")
    public String IngresarCrearVestidoDama(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        return "crearVestidoDama";
    }

    @PostMapping("/editarVestidoDama")
    public String editarVestidoDama(
            @RequestParam("altura") String altura,
            @RequestParam("cantPiezas") String cantPiezas,
            @RequestParam("pedreria") String pedreria,
            @RequestParam("color") String color,
            @RequestParam("marca") String marca,
            @RequestParam("refer") String refer,
            HttpServletRequest request) {

        VestidoDama vestidoDama = (VestidoDama) request.getSession().getAttribute("vestidoDamaEditar");

        vestidoDama.setAltura(altura);
        vestidoDama.setCantPiezas(cantPiezas);
        vestidoDama.setPedreria(pedreria);
        vestidoDama.setColor(color);
        vestidoDama.setMarca(marca);
        vestidoDama.setRefer(refer);

        control.editarVestidoDama(vestidoDama);

        return "redirect:/verVestidosDama";
    }

    //faltan las imagenes, la tabla de imagenes y rutas
    @PostMapping("/crearVestidoDama")
    public String crearVestidoDama(
            @RequestParam("altura") String altura,
            @RequestParam("cantPiezas") String cantPiezas,
            @RequestParam("pedreria") String pedreria,
            @RequestParam("color") String color,
            @RequestParam("marca") String marca,
            @RequestParam("refer") String refer,
            HttpServletRequest request) {

        VestidoDama vestidoDama = new VestidoDama();

        vestidoDama.setAltura(altura);
        vestidoDama.setCantPiezas(cantPiezas);
        vestidoDama.setPedreria(pedreria);
        vestidoDama.setColor(color);
        vestidoDama.setMarca(marca);
        vestidoDama.setRefer(refer);

        control.crearVestidoDama(vestidoDama);

        return "redirect:/verVestidosDama";
    }
}

