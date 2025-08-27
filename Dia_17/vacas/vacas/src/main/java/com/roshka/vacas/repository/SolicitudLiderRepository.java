package com.roshka.vacas.repository;

import com.roshka.vacas.entity.SolicitudLider;
import com.roshka.vacas.entity.SolicitudLiderId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudLiderRepository extends JpaRepository<SolicitudLider, SolicitudLiderId> {
}
