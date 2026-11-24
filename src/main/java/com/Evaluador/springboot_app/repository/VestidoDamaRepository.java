package com.Evaluador.springboot_app.repository;

import com.Evaluador.springboot_app.model.VestidoDama;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VestidoDamaRepository extends JpaRepository<VestidoDama, Long> {
}
