package com.grupo1.infraestructure.repository;

import com.grupo1.infraestructure.entity.ConsultaMedicaEntity;
import com.grupo1.infraestructure.entity.TriajeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsultaMedicaRepository extends JpaRepository<ConsultaMedicaEntity, Long> {

    List<ConsultaMedicaEntity> findByEstado(Boolean estado);

}
