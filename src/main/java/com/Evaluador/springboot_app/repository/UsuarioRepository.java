package com.Evaluador.springboot_app.repository;

import com.Evaluador.springboot_app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Consulta derivada por nombre de métodos
    Optional<Usuario> findByCorreo(String correo);
}

