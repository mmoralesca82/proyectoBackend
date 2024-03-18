package com.mshinsurance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="asegurado")
public class AseguradoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;

    @Column(name="numero_documento", nullable = false, unique = true)
    private String numDoc;

    @Column(nullable = false)
    private Boolean enabled = true;

    @OneToOne(optional = false)
    @JoinColumn(name = "cob_lab_fk")
    private CoberturaLaboratorioEntity cobLab;

    @OneToOne(optional = false)
    @JoinColumn(name = "cob_med_fk")
    private CoberturaMedicaEntity cobMed;




}
