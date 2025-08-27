package com.roshka.vacas.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="cargos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cargo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_cargo") private Long id;
    @Column(name="nombre", length=255) private String nombre;
}
