package com.Evaluador.springboot_app.repository;

import com.Evaluador.springboot_app.model.Prenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrendaRepository extends JpaRepository<Prenda, Long> {
}
