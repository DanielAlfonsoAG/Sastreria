
package com.Evaluador.springboot_app.repository;

import com.Evaluador.springboot_app.model.Disfraz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisfrazRepository extends JpaRepository<Disfraz, Long> {
}
