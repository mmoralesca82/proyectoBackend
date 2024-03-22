package com.grupo1.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name="triaje")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TriajeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long idTriaje;

//    @Column(nullable = false)
    private Timestamp fechaTriaje;
//    @Column(nullable = false)
    private Double tempCorporal;
//    @Column(nullable = false, length = 20)
    @Column(length = 20)
    private String presionArterial;
//    @Column(nullable = false)
    private Integer satOxigeno;
//    @Column(nullable = false, length = 100)
    @Column(length = 100)
    private String sintoma;
//    @Column(nullable = true, length = 100)
    @Column(length = 100)
    private String observaciones;

    @Column(nullable = false, length = 25)
    private String usuarioCreacion;
    @Column(nullable = false)
    private Timestamp fechaCreacion;
    @Column(length = 25)
    private String usuarioModificacion;
    private Timestamp fechaModificacion;
    @Column(length = 25)
    private String usuarioEliminacion;
    private Timestamp fechaEliminacion;
    @Column(nullable = false)
    private Boolean estado;

//    @JsonIgnore
//    @JsonIgnoreProperties("consulta")
//    @OneToMany(mappedBy = "triaje")
//    private Set<ConsultaMedicaEntity> consulta;

}
