package com.mshinsurance.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cob_lab")
public class CoberturaLaboratorioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double cobLab1;

    @Column(nullable = false)
    private Double cobLab2;

    @Column(nullable = false)
    private Double cobLab3;



}
