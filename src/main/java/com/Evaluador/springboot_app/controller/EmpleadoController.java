package com.Evaluador.springboot_app.controller;

import com.Evaluador.springboot_app.model.Cliente;
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
public class EmpleadoController {

    @Autowired
    private Controladora control;

    @GetMapping("/verEmpleados")
    public String ListaDeEmpleados(HttpServletRequest request) {
        List<Empleado> listaEmpleados = control.listarEmpleados();
        HttpSession session = request.getSession();
        session.setAttribute("listaEmpleados", listaEmpleados);
        return "verEmpleados";
    }

    @PostMapping("/eliminarEmpleado")
    public String eliminarEmple(@RequestParam("id") String id,
                              HttpServletRequest request) {
        int id_empleado = Integer.parseInt(id);
        control.borrarEmpleado(id_empleado);
        return "principal";
    }

    @GetMapping("/editarEmpleado")
    public String mostrarFormularioEdicion(HttpServletRequest request, @RequestParam("id") Long id, Model model) {
        Optional<Empleado> EmpleadoOptional = control.traerEmpleado(id);

        if (EmpleadoOptional.isPresent()) {
            Empleado empleado = EmpleadoOptional.get();

            model.addAttribute("empleEditar", empleado);

            List<Usuario> usuarios = control.listarUsuarios();
            model.addAttribute("usuarios", usuarios);

            return "editarEmpleado"; // tu template Thymeleaf
        } else {
            return "redirect:/verEmpleados";
        }
    }

    @GetMapping("/crearEmpleado")
    public String IngresarCrearEmpleado(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }

        List<Usuario> usuarios = control.listarUsuarios();
        model.addAttribute("usuarios", usuarios);

        return "crearEmpleado";
    }

    @PostMapping("/editarEmpleado")
    public String editarEmpleado(
            @RequestParam("cargo") String cargo,
            @RequestParam("direccion") String direccion,
            @RequestParam("nombres") String nombres,
            @RequestParam("telefono") String telefono,
            @RequestParam("id_usuario") String id_usuario,
            HttpServletRequest request) {

        Empleado emp = (Empleado) request.getSession().getAttribute("empleEditar");
        if (emp == null) {
            return "redirect:/verEmpleados";
        }

        long usuarioId = Long.parseLong(id_usuario);

        Usuario usu = control.traerunUsuario(usuarioId); // Usuario o null

        emp.setCargo(cargo);
        emp.setDireccion(direccion);
        emp.setNombres(nombres);
        emp.setTelefono(telefono);
        emp.setUnUsuario(usu);

        control.editarEmpleado(emp);

        return "redirect:/verEmpleados";
    }

    //faltan las imagenes, la tabla de imagenes y rutas
    @PostMapping("/crearEmpleado")
    public String crearEmpleado(
            @RequestParam("cargo") String cargo,
            @RequestParam("direccion") String direccion,
            @RequestParam("nombres") String nombres,
            @RequestParam("telefono") String telefono,
            @RequestParam("id_usuario") String id_usuario,
            HttpServletRequest request) {

        long usuarioId = Long.parseLong(id_usuario);

        Usuario usuario = control.traerunUsuario(usuarioId);
        Empleado emp = new Empleado();

        emp.setCargo(cargo);
        emp.setDireccion(direccion);
        emp.setNombres(nombres);
        emp.setTelefono(telefono);
        emp.setUnUsuario(usuario);

        control.crearEmpleado(emp);

        return "redirect:/verEmpleados";
    }
}
