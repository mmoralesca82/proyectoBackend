package com.grupo1.infraestructure.repository;

import com.grupo1.infraestructure.entity.ConsultaMedicaEntity;
import com.grupo1.infraestructure.entity.ProcedimientoMedicoEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProcedimientoMedicoRepository extends JpaRepository<ProcedimientoMedicoEntity, Long> {

    List<ProcedimientoMedicoEntity> findByConsulta(ConsultaMedicaEntity consulta);
}
