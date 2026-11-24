package com.Evaluador.springboot_app.repository;

import com.Evaluador.springboot_app.model.TrajeCaballero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrajeCaballeroRepository extends JpaRepository<TrajeCaballero, Long> {
}
