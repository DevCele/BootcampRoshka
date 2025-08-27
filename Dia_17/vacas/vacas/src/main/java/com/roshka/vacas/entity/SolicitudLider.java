package com.roshka.vacas.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="solicitud_lideres")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class SolicitudLider {

    @EmbeddedId
    private SolicitudLiderId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idSolicitud")
    @JoinColumn(name="id_solicitud")
    private Solicitud solicitud;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idLider")
    @JoinColumn(name="id_lider")
    private Usuario lider;
}
