package com.Evaluador.springboot_app.controller;

import com.Evaluador.springboot_app.model.TrajeCaballero;
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
public class TrajeCaballeroController {

    @Autowired
    private Controladora control;

    @GetMapping("/verTrajeCaballeros")
    public String ListaDeTrajes(HttpServletRequest request) {
        List<TrajeCaballero> listaTrajeCaballero = control.listarTrajes();
        HttpSession session = request.getSession();
        session.setAttribute("listaTrajeCaballero", listaTrajeCaballero);
        return "verTrajeCaballeros";
    }

    @PostMapping("/eliminarTrajeCaballero")
    public String eliminarTrajeCaballero(@RequestParam("id") String id,
                                  HttpServletRequest request) {
        int id_trajeCaballero = Integer.parseInt(id);
        control.borrarTrajeCaballero(id_trajeCaballero);
        return "principal";
    }

    @GetMapping("/editarTrajeCaballero")
    public String IngresarEditarTrajeCaballero(@RequestParam("id") long id,
                                        HttpServletRequest request, Model model) {

        Optional<TrajeCaballero> optionalTrajeCaballero = control.traerTrajeCaballero(id);

        if (optionalTrajeCaballero.isPresent()) {

            HttpSession misession = request.getSession();

            TrajeCaballero trajeCaballero = optionalTrajeCaballero.get();
            request.getSession().setAttribute("trajeCaballeroEditar", trajeCaballero);
            model.addAttribute("trajeCaballeroEditar", trajeCaballero);

            if (misession.getAttribute("usuario") == null) {
                return "redirect:/login";
            }
            return "editarTrajeCaballero";

        } else {
            // Redirige si el usuario no existe
            return "redirect:/verTrajeCaballeros";
        }
    }
    @GetMapping("/crearTrajeCaballero")
    public String IngresarCrearTrajeCaballero(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        return "crearTrajeCaballero";
    }

    @PostMapping("/editarTrajeCaballero")
    public String editarTrajeCaballero(
            @RequestParam("aderezo") String aderezo,
            @RequestParam("tipo") String tipo,
            @RequestParam("color") String color,
            @RequestParam("marca") String marca,
            @RequestParam("refer") String refer,
            HttpServletRequest request) {

        TrajeCaballero trajeCa = (TrajeCaballero) request.getSession().getAttribute("trajeCaballeroEditar");

        trajeCa.setAderezo(aderezo);
        trajeCa.setTipo(tipo);
        trajeCa.setColor(color);
        trajeCa.setMarca(marca);
        trajeCa.setRefer(refer);

        control.editarTrajeCaballero(trajeCa);

        return "redirect:/verTrajeCaballeros";
    }

    //faltan las imagenes, la tabla de imagenes y rutas
    @PostMapping("/crearTrajeCaballero")
    public String crearTrajeCaballero(
            @RequestParam("aderezo") String aderezo,
            @RequestParam("tipo") String tipo,
            @RequestParam("color") String color,
            @RequestParam("marca") String marca,
            @RequestParam("refer") String refer,
            HttpServletRequest request) {

        TrajeCaballero trajeCa = new TrajeCaballero();

        trajeCa.setAderezo(aderezo);
        trajeCa.setTipo(tipo);
        trajeCa.setColor(color);
        trajeCa.setMarca(marca);
        trajeCa.setRefer(refer);

        control.crearTrajeCaballero(trajeCa);

        return "redirect:/verTrajeCaballeros";
    }
}

