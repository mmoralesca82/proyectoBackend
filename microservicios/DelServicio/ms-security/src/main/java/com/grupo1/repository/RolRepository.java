package com.grupo1.repository;



import com.grupo1.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<RolEntity,Long> {
    Optional<RolEntity> findByNombreRol(String nombreRol);


}
