package com.Evaluador.springboot_app.controller;

import com.Evaluador.springboot_app.model.Cliente;
import com.Evaluador.springboot_app.model.Disfraz;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ClienteController {

    @Autowired
    private Controladora control;

    @GetMapping("/verClientes")
    public String ListaDeClientes(HttpServletRequest request) {
        List<Cliente> listaClientes = control.listarClientes();
        if (listaClientes == null) listaClientes = new ArrayList<>();
        HttpSession session = request.getSession();
        session.setAttribute("listaClientes", listaClientes);
        return "verClientes";
    }

    @PostMapping("/eliminarCliente")
    public String eliminarClient(@RequestParam("id") String id,
                                HttpServletRequest request) {
        int id_clientes = Integer.parseInt(id);
        control.borrarCliente(id_clientes);
        return "principal";
    }

    @GetMapping("/editarCliente")
    public String mostrarFormularioEdicion(HttpServletRequest request, @RequestParam("id") Long id, Model model) {
        Optional<Cliente> clienteOptional = control.traerCliente(id);

        if (clienteOptional.isPresent()) {
            Cliente cliente = clienteOptional.get();

            model.addAttribute("clientEditar", cliente);

            List<Usuario> usuarios = control.listarUsuarios();
            model.addAttribute("usuarios", usuarios);

            return "editarCliente"; // tu template Thymeleaf
        } else {
            return "redirect:/verClientes";
        }
    }

    @GetMapping("/crearCliente")
    public String IngresarCrearCliente(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        List<Usuario> usuarios = control.listarUsuarios();
        model.addAttribute("usuarios", usuarios);

        return "crearCliente";
    }

    @PostMapping("/editarCliente")
    public String editarCliente(
            @RequestParam("direccion") String direccion,
            @RequestParam("nombres") String nombres,
            @RequestParam("telefono") String telefono,
            @RequestParam("id_usuario") String id_usuario,
            HttpServletRequest request) {

        Cliente  cli  = (Cliente) request.getSession().getAttribute("clientEditar");
        if (cli == null) {
            return "redirect:/verClientes";
        }

        long usuarioId = Long.parseLong(id_usuario);

        Usuario usu = control.traerunUsuario(usuarioId); // Usuario o null

        cli.setDireccion(direccion);
        cli.setNombres(nombres);
        cli.setTelefono(telefono);
        cli.setUnUsuario(usu);

        control.editarCliente(cli);

        return "redirect:/verClientes";
    }

    //faltan las imagenes, la tabla de imagenes y rutas
    @PostMapping("/crearCliente")
    public String crearEmpleado(
            @RequestParam("direccion") String direccion,
            @RequestParam("nombres") String nombres,
            @RequestParam("telefono") String telefono,
            @RequestParam("id_usuario") String id_usuario,
            HttpServletRequest request) {

        long usuarioId = Long.parseLong(id_usuario);

        Usuario usuario = control.traerunUsuario(usuarioId);
        Cliente cli = new Cliente();

        cli.setDireccion(direccion);
        cli.setNombres(nombres);
        cli.setTelefono(telefono);
        cli.setUnUsuario(usuario);

        control.crearCliente(cli);

        return "redirect:/verClientes";
    }
}

