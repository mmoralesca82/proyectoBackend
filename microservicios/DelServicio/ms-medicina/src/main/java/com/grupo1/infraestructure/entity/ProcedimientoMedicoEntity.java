package com.grupo1.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;


@Entity
@Table(name="proced_medico")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
//@NamedQuery(name = "ProcedimientoMedicoEntity.findByIdConsultaFk", query = "select a from ProcedimientoMedicoEntity a where a.IdConsultaFk=:IdConsultaFk")
public class ProcedimientoMedicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProcMedico;
    @Column(nullable = false, length = 50)
    private String tipoProcedimiento;
    @Column(nullable = true, length = 120)
    private String notasProcedimiento;
    @Column(nullable = false)
    private Timestamp fechaProcedimiento;


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


    @JsonIgnore
//    @JsonIgnoreProperties("consulta")
    @ManyToOne
    @JoinColumn(name = "id_consulta_fk")
    private ConsultaMedicaEntity consulta;


}
