package com.grupo1.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name="analisis_clinico")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnalisisClinicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAnalisis;

    @Column(nullable = true, length = 20)
    private String nombreSeguro; //Obtenido con consulta al API HInsurance via MS-ExternalAPI
    @Column(nullable = true, columnDefinition = "Float default 0.00")
    private Float coberturaSeguro;//Obtenido con consulta al API HInsurance via MS-ExternalAPI
    @Column(nullable = true, length = 120)
    private String resultado;
    @Column(length = 25)
    private String usuarioRecepcion;
    private Timestamp fechaRecepcion;

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
    @JsonIgnoreProperties("nombreAnalisis")
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nombre_analisis_fk")
    private NombreAnalisisEntity nombreAnalisis;


    //    @JsonIgnore
    @JsonIgnoreProperties("consulta")
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_consulta_fk")
    private ConsultaMedicaEntity consulta;

}
