package com.grupo1.infraestructure.repository;

import com.grupo1.infraestructure.entity.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<PacienteEntity, Long> {
}
