package com.Evaluador.springboot_app.controller;

import com.Evaluador.springboot_app.model.Disfraz;
import com.Evaluador.springboot_app.model.Empleado;
import com.Evaluador.springboot_app.model.Usuario;
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
public class DisfrazController {

    @Autowired
    private Controladora control;

    @GetMapping("/verDisfraces")
    public String ListaDeDisfraces(HttpServletRequest request) {
        List<Disfraz> listaDisfraces = control.listarDisfraces();
        HttpSession session = request.getSession();
        session.setAttribute("listaDisfraces", listaDisfraces);
        return "verDisfraces";
    }

    @PostMapping("/eliminarDisfraz")
    public String eliminarDisfraz(@RequestParam("id") String id,
                                 HttpServletRequest request) {
        int id_disfraz = Integer.parseInt(id);
        control.borrarDisfraz(id_disfraz);
        return "principal";
    }

    @GetMapping("/editarDisfraz")
    public String IngresarEditarDisfraz(@RequestParam("id") long id,
                                       HttpServletRequest request, Model model) {

        Optional<Disfraz> optionalDisfraz = control.traerDisfraz(id);

        if (optionalDisfraz.isPresent()) {

            HttpSession misession = request.getSession();

            Disfraz disfraz = optionalDisfraz.get();
            model.addAttribute("disfrazEditar", disfraz);
            request.getSession().setAttribute("disfrazEditar", disfraz);

            if (misession.getAttribute("usuario") == null) {
                return "redirect:/login";
            }
            return "editarDisfraz";

        } else {
            // Redirige si el usuario no existe
            return "redirect:/verDisfraces";
        }
    }

    @GetMapping("/crearDisfraz")
    public String IngresarCrearDisfraz(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        return "crearDisfraz";
    }

    @PostMapping("/editarDisfraz")
    public String editarDisfraz(
            @RequestParam("nombre") String nombre,
            @RequestParam("color") String color,
            @RequestParam("marca") String marca,
            @RequestParam("refer") String refer,
            HttpServletRequest request) {

        Disfraz dis = (Disfraz) request.getSession().getAttribute("disfrazEditar");

        dis.setNombre(nombre);
        dis.setColor(color);
        dis.setMarca(marca);
        dis.setRefer(refer);

        control.editarDisfraz(dis);

        return "redirect:/verDisfraces";
    }

    //faltan las imagenes, la tabla de imagenes y rutas
    @PostMapping("/crearDisfraz")
    public String crearDisfraz(
            @RequestParam("nombre") String nombre,
            @RequestParam("color") String color,
            @RequestParam("marca") String marca,
            @RequestParam("refer") String refer,
            HttpServletRequest request) {

        Disfraz dis = new Disfraz();

        dis.setNombre(nombre);
        dis.setColor(color);
        dis.setMarca(marca);
        dis.setRefer(refer);

        control.crearDisfraz(dis);

        return "redirect:/verDisfraces";
    }
}

