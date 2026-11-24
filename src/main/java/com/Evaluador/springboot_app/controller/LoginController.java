package com.Evaluador.springboot_app.controller;

import com.Evaluador.springboot_app.config.JwtUtil;
import com.Evaluador.springboot_app.model.Usuario;
import com.Evaluador.springboot_app.service.Controladora;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private Controladora control;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostrar formulario de login
    @GetMapping("/login")
    public String mostrarLogin(HttpServletRequest request) {
        request.getSession().invalidate(); // Cierra cualquier sesión previa
        HttpSession misession = request.getSession(true);

        if (request.getParameter("registrado") != null) {
            misession.setAttribute("mensajeRegistro", "¡Registro exitoso! Ahora puedes iniciar sesión.");
        }

        misession.setAttribute("error", false);
        return "login";
    }

    // Procesar login manual
    @PostMapping("/login")
    public String login(
            @RequestParam("correo") String correo,
            @RequestParam("contrasenia") String contrasenia,
            HttpServletRequest request) {

        HttpSession misession = request.getSession(true);
        Usuario usuario = control.buscarPorCorreo(correo);

        if (usuario != null && passwordEncoder.matches(contrasenia, usuario.getContrasenia())) {
            //  Generar token JWT
            String token = jwtUtil.generateToken(usuario.getCorreo());

            //  Guardar en sesión
            misession.setAttribute("usuario", usuario.getCorreo());
            misession.setAttribute("id_usuario", usuario.getId());
            misession.setAttribute("image", usuario.getImage());
            misession.setAttribute("rol", usuario.getRol());
            misession.setAttribute("usuarioObj", usuario);
            misession.setAttribute("jwt", token); // token guardado si lo necesitas para peticiones

            return "redirect:/principal";
        }

        // Login fallido
        misession.setAttribute("error", true);
        return "redirect:/login?error";
    }
    // Redirigir raíz al login
    @GetMapping("/inicio")
    public String rediRaiz() {
        return "inicio";
    }
    // Redirigir raíz al login
    @GetMapping("/")
    public String redireccionRaiz() {
        return "inicio";
    }

    // Vista principal después del login
    @GetMapping("/principal")
    public String principal(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        return "principal";
    }

    // Cerrar sesión
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}
