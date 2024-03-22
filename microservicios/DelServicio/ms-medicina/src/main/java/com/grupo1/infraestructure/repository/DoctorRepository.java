package com.grupo1.infraestructure.repository;

import com.grupo1.infraestructure.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<DoctorEntity, Long> {
}
