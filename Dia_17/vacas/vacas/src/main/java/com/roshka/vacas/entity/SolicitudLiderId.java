package com.roshka.vacas.entity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode
public class SolicitudLiderId implements Serializable {
    @Column(name="id_solicitud") private Long idSolicitud;
    @Column(name="id_lider") private Long idLider; // referencia a usuarios.id_usuario
}
