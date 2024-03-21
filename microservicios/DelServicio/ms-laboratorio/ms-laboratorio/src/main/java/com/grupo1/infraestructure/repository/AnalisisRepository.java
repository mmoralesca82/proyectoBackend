package com.grupo1.infraestructure.repository;

import com.grupo1.infraestructure.entity.AnalisisClinicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnalisisRepository extends JpaRepository<AnalisisClinicoEntity, Long> {

    List<AnalisisClinicoEntity> findByEstado(Boolean estado);
}
