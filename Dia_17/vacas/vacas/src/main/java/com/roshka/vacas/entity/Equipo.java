package com.roshka.vacas.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="equipos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Equipo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_equipo") private Long id;
    @Column(name="nombre", length=255) private String nombre;
}
