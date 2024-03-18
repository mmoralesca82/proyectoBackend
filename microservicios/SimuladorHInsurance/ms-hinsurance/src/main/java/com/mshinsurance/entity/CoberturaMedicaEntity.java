package com.mshinsurance.entity;

import jakarta.persistence.*;
import lombok.Getter;


@Entity
@Getter
@Table(name="cob_med")
public class CoberturaMedicaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Getter
    @Column(nullable = false)
    private Double cobMed1;

    @Getter
    @Column(nullable = false)
    private Double cobMed2;

    @Getter
    @Column(nullable = false)
    private Double cobMed3;


}
