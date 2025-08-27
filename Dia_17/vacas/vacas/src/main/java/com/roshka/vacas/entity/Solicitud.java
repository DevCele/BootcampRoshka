// com/roshka/vacas/entity/Solicitud.java
package com.roshka.vacas.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity @Table(name="solicitudes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Solicitud {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_solicitud") private Long id;

    @Column(name="fecha_inicio") private LocalDate fechaInicio;
    @Column(name="fecha_fin") private LocalDate fechaFin;

    @Column(name="estado") private Boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_usuario")
    private Usuario usuario;

    @Column(name="cantidad_dias") private Integer cantidadDias;
    @Column(name="numero_aprobaciones") private Integer numeroAprobaciones;
    @Column(name="comentario") private String comentario;
    @Column(name="rechazado") private Boolean rechazado;
}
