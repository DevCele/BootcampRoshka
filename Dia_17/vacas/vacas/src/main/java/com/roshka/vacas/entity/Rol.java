package com.roshka.vacas.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Rol {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_rol") private Long id;
    @Column(name="nombre") private String nombre;
}
