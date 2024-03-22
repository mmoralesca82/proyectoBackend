package com.grupo1.infraestructure.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name="consultas_medicas")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaMedicaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idConsulta;

    private Date fechaConsulta;
    @Column(nullable = true, length = 150)
    private String sintomas;
    @Column(nullable = true, length = 150)
    private String diagnostico;
    @Column(nullable = true, length = 150)
    private String tratamiento;
    @Column(length = 150)
    private String notasMedicas;
    @Column(nullable = true, length = 20)
    private String nombreSeguro;
    @Column(nullable = true, columnDefinition = "Float default 0.00")
    private Float coberturaSeguro;

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
    @JsonIgnoreProperties("doctor")
    @ManyToOne(optional = false)
    @JoinColumn(name="id_doctor_fk", nullable = false)
    private DoctorEntity doctor;

//    @JsonIgnore
    @JsonIgnoreProperties("paciente")
    @ManyToOne(optional = false)
    @JoinColumn(name="id_paciente_fk", nullable = false)
    private PacienteEntity paciente;

//    @JsonIgnore
    @JsonIgnoreProperties("triaje")
    @ManyToOne(optional = true)
    @JoinColumn(name="id_triaje_fk", nullable = false)
    private TriajeEntity triaje;


//    @JsonIgnoreProperties("analisis")
//    @OneToMany(mappedBy = "consulta")
//    private Set<AnalisisClinicoEntity> analisis;

    @JsonIgnoreProperties("procedimiento")
    @OneToMany(mappedBy="consulta")
    private Set<ProcedimientoMedicoEntity> procedimiento;


}
