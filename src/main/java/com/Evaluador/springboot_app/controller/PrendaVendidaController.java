package com.Evaluador.springboot_app.controller;

import com.Evaluador.springboot_app.model.*;
import com.Evaluador.springboot_app.service.Controladora;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class PrendaVendidaController {

    @Autowired
    private Controladora control;

    // Mostrar el formulario para crear examen
    @GetMapping("/crearVenta")
    public String mostrarFormularioVenta(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        String usuario = (String) session.getAttribute("usuario");
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        model.addAttribute("prendasvendidas", new Prendasvendidas());

        model.addAttribute("clientes", control.listarClientes());
        model.addAttribute("vestidos", control.listarVestidoDama());
        model.addAttribute("trajes", control.listarTrajes());
        model.addAttribute("disfraces", control.listarDisfraces());

        return "crearVenta";
    }

    @PostMapping("/crearVenta")
    public String procesarVenta(@ModelAttribute("prendasvendidas") Prendasvendidas venta,
                                @RequestParam(value = "disfrazId", required = false) Long disfrazId,
                                @RequestParam(value = "trajeId", required = false) Long trajeId,
                                @RequestParam(value = "vestidoId", required = false) Long vestidoId,
                                @RequestParam("clienteId") Long clienteId,
                                HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioObj");
        if (usuario == null) {
            return "redirect:/login";
        }

        if ((disfrazId == null && trajeId == null && vestidoId == null) || clienteId == null) {
            model.addAttribute("errorTitulo", "Debe seleccionar prenda y cliente.");
            model.addAttribute("clientes", control.listarClientes());
            model.addAttribute("vestidos", control.listarVestidoDama());
            model.addAttribute("trajes", control.listarTrajes());
            model.addAttribute("disfraces", control.listarDisfraces());
            return "crearVenta";
        }

        Prenda prenda = null;
        if (disfrazId != null) prenda = control.buscarDisfrazPorId(disfrazId);
        if (trajeId != null) prenda = control.buscarTrajePorId(trajeId);
        if (vestidoId != null) prenda = control.buscarVestidoPorId(vestidoId);

        Cliente cliente = control.buscarClientePorId(clienteId);

        venta.setPrenda(prenda);
        venta.setCliente(cliente);
        venta.setCreadoPor(usuario);

        control.guardarPrendaVendida(venta);

        return "redirect:/verVentas";
    }
    @GetMapping("/verVentas")
    public String ListaDeVentas(HttpServletRequest request) {
        List<Prendasvendidas> listaVentas = control.listarVentas();
        HttpSession session = request.getSession();
        session.setAttribute("listaVentas", listaVentas);
        return "verVentas";
    }

    @PostMapping("/eliminarVenta")
    public String eliminarVenta(@RequestParam("id") long id,
                                         HttpServletRequest request) {
        control.borrarVenta(id);
        return "redirect:/verVentas";
    }

    @GetMapping("/editarVenta")
    public String IngresarEditarVenta(@RequestParam("id") long id,
                                               HttpServletRequest request, Model model) {

        Optional<Prendasvendidas> optionalVentas = control.traerVentas(id);

        if (optionalVentas.isPresent()) {

            HttpSession misession = request.getSession();

            Prendasvendidas ventas = optionalVentas.get();
            request.getSession().setAttribute("ventaEditar", ventas);
            model.addAttribute("ventaEditar", ventas);

            model.addAttribute("clientes", control.listarClientes());
            model.addAttribute("vestidos", control.listarVestidoDama());
            model.addAttribute("trajes", control.listarTrajes());
            model.addAttribute("disfraces", control.listarDisfraces());

            // *** NUEVO — Detectar automáticamente el tipo de prenda ***
            Prenda prenda = ventas.getPrenda();

            boolean esVestido = prenda instanceof VestidoDama;
            boolean esTraje = prenda instanceof TrajeCaballero;
            boolean esDisfraz = prenda instanceof Disfraz;

            model.addAttribute("esVestido", esVestido);
            model.addAttribute("esTraje", esTraje);
            model.addAttribute("esDisfraz", esDisfraz);

            if (misession.getAttribute("usuario") == null) {
                return "redirect:/login";
            }
            return "editarVenta";

        } else {
            // Redirige si el usuario no existe
            return "redirect:/verVentas";
        }
    }

    @PostMapping("/editarVenta")
    public String editarVenta(
            @ModelAttribute("ventaEditar") Prendasvendidas venta,
            @RequestParam(value = "disfrazId", required = false) Long disfrazId,
            @RequestParam(value = "trajeId", required = false) Long trajeId,
            @RequestParam(value = "vestidoId", required = false) Long vestidoId,
            @RequestParam("clienteId") Long clienteId,
            HttpSession session, Model model) {

        Usuario usuario = (Usuario) session.getAttribute("usuarioObj");
        if (usuario == null) {
            return "redirect:/login";
        }

        // Validación básica
        if ((disfrazId == null && trajeId == null && vestidoId == null) || clienteId == null) {
            model.addAttribute("errorTitulo", "Debe seleccionar prenda y cliente.");
            model.addAttribute("clientes", control.listarClientes());
            model.addAttribute("vestidos", control.listarVestidoDama());
            model.addAttribute("trajes", control.listarTrajes());
            model.addAttribute("disfraces", control.listarDisfraces());
            return "editarVenta";
        }

        // Determinar la prenda correcta
        Prenda prenda = null;
        if (vestidoId != null) prenda = control.buscarVestidoPorId(vestidoId);
        if (trajeId != null) prenda = control.buscarTrajePorId(trajeId);
        if (disfrazId != null) prenda = control.buscarDisfrazPorId(disfrazId);

        Cliente cliente = control.buscarClientePorId(clienteId);

        venta.setPrenda(prenda);
        venta.setCliente(cliente);
        venta.setCreadoPor(usuario);

        control.editarVentas(venta);

        return "redirect:/verVentas";
    }


}