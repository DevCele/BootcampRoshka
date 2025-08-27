package com.roshka.vacas.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name="documentos_permisos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DocumentoPermiso {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_documento") private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_solicitud")
    private Solicitud solicitud;

    @Column(name="url_documento", length=255) private String urlDocumento;
    @Column(name="fecha_subida") private LocalDateTime fechaSubida;
}
