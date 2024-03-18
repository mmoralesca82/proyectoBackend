package com.mshinsurance.repository;

import com.mshinsurance.entity.AseguradoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AseguradoRepository extends JpaRepository<AseguradoEntity, Long> {

    Optional<AseguradoEntity> findByNumDoc(String numDoc);
}
