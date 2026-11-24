package com.Evaluador.springboot_app.repository;

import com.Evaluador.springboot_app.model.Datos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DatosRepository extends JpaRepository<Datos, Long> {
    // Consulta derivada por nombre de métodos
    Optional<Datos> findByRuta(String ruta);
}
