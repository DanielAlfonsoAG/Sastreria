package com.Evaluador.springboot_app.controller;

import com.Evaluador.springboot_app.model.Datos;
import com.Evaluador.springboot_app.model.Usuario;
import com.Evaluador.springboot_app.service.Controladora;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Controller
public class UsuarioController {

    private String[] extens = {".jpg",".png"};
    @Autowired
    private Controladora control;

    private String saveFile(Part part, File pathUploads){
        String pathAbsolute="";
        try {
            Path path = Paths.get(part.getSubmittedFileName());
            String filename = path.getFileName().toString();
            InputStream input = part.getInputStream();
            if (input != null) {
                File file = new File(pathUploads,filename);
                pathAbsolute = file.getPath();

                Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pathAbsolute;
    }
    private boolean isExtension(String fileName, String[] extensions){
        for (String et: extensions) {
            if (fileName.toLowerCase().endsWith(et)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping("/verUsuarios")
    public String ListaDeUsuarios(HttpServletRequest request) {
        List<Usuario> listaUsuarios = control.listarUsuarios();
        HttpSession session = request.getSession();
        session.setAttribute("listaUsuarios", listaUsuarios);
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
        return "verUsuarios";
    }

    @PostMapping("/eliminarUsuario")
    public String eliminarUsu(@RequestParam("id") String id,
                              HttpServletRequest request) {
        int id_usuario = Integer.parseInt(id);
        control.borrarUsuario(id_usuario);
        return "principal";
    }

    @GetMapping("/editarUsuario")
    public String IngresarEditarUsu(@RequestParam("id") String id,
                              HttpServletRequest request) {
        long id_usuario = Long.parseLong(id);
        Optional<Usuario> optionalUsuario = control.traerUsuario(id_usuario);

        if (optionalUsuario.isPresent()) {
            HttpSession misession = request.getSession();
            Usuario usuario = optionalUsuario.get();
            request.getSession().setAttribute("usuEditar", usuario);
            if (misession.getAttribute("usuario") == null) {
                return "redirect:/login";
            }
            return "editarUsuario";
        } else {
            // Redirige si el usuario no existe
            return "verUsuarios";
        }
    }
    @GetMapping("/crearUsuario")
    public String IngresarCrearUsuario(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String usuario = (String) session.getAttribute("usuario");
        if (session.getAttribute("usuario") == null) {
            return "redirect:/login";
        }
            return "crearUsuario";
    }

    @PostMapping("/editarUsuario")
    public String editarUsu(
            @RequestParam("correo") String correo,
            @RequestParam("contrasenia") String contrasenia,
            @RequestParam("rol") String rol,
            @RequestParam("image") Part part,
            HttpServletRequest request) {
        Usuario usu = (Usuario) request.getSession().getAttribute("usuEditar");

        if (part != null && part.getSize() > 0 && isExtension(part.getSubmittedFileName(), extens)) {

            Optional<Datos> datos= control.traerDatos(1);
            String pathFiles= datos.get().getRuta();
            File uploads = new File(pathFiles);

            String archivo = saveFile(part, uploads);
            usu.setImage("img/" + part.getSubmittedFileName());
        }

        usu.setCorreo(correo);
        usu.setContrasenia(contrasenia);
        usu.setRol(rol);


        control.editarUsuario(usu);

        return "principal";
    }

    //faltan las imagenes, la tabla de imagenes y rutas
    @PostMapping("/crearUsuario")
    public String crearUsuario(
            @RequestParam("correo") String correo,
            @RequestParam("contrasenia") String contrasenia,
            @RequestParam("rol") String rol,
            @RequestParam("image") Part part,
            HttpServletRequest request) {

        Usuario usuario = new Usuario();
        if (part != null && part.getSize() > 0 && isExtension(part.getSubmittedFileName(), extens)) {

            Optional<Datos> datos= control.traerDatos(1);
            String pathFiles= datos.get().getRuta();
            File uploads = new File(pathFiles);

            String archivo = saveFile(part, uploads);
            usuario.setImage("img/" + part.getSubmittedFileName());
        } else {
        usuario.setImage("perfil.png"); // imagen por defecto si no sube
        }

        usuario.setCorreo(correo);
        usuario.setContrasenia(contrasenia);
        usuario.setRol(rol); //
        if (!control.existeUsuario(correo)) {
            control.registrar(usuario);

            HttpSession session = request.getSession();
            session.setAttribute("mensajeRegistro","¡Registro exitoso!");

            return "redirect:/login?registrado";
        }
        return "redirect:/login?existe";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam("correo") String correo,
            @RequestParam("contrasenia") String contrasenia,
            @RequestParam("rol") String rol,
            HttpServletRequest request) {

        Usuario usuario = new Usuario();

        usuario.setCorreo(correo);
        usuario.setContrasenia(contrasenia);
        usuario.setRol(rol); //
        usuario.setImage("default.jpg"); // imagen por defecto
        if (!control.existeUsuario(correo)) {
            control.registrar(usuario);

            HttpSession session = request.getSession();
            session.setAttribute("mensajeRegistro","¡Registro exitoso!");

            return "redirect:/login?registrado";
        }
        return "redirect:/login?existe";
    }
}
