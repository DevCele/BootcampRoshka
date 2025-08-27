package com.roshka.vacas.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;


@Entity @Table(name="usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_usuario") private Long id;

    @Column(name="nombre")  private String nombre;
    @Column(name="apellido") private String apellido;

    @Column(name="nro_cedula") private Integer nroCedula;
    @Column(name="correo") private String correo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_rol")
    private Rol rol;

    @Column(name="fecha_ingreso") private LocalDate fechaIngreso;

    //antiguedad
   @Transient
   private Period antiguedad;


    @Column(name="dias_vacaciones") private Integer diasVacaciones;
    @Column(name="estado") private Boolean estado;


    @Column(name="contrasena") private String contrasena;

    @Column(name="telefono", length=255) private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_equipo")
    private Equipo equipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_cargo")
    private Cargo cargo;

    @Column(name="fecha_nacimiento") private LocalDate fechaNacimiento;
    @Column(name="dias_vacaciones_restante") private Integer diasVacacionesRestante;
    @Column(name="requiere_cambio_contrasena") private Boolean requiereCambioContrasena;
}
