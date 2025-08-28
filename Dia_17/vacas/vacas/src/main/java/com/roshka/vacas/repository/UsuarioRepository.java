package com.roshka.vacas.repository;

import com.roshka.vacas.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    @Query(
            value = """
            select u from Usuario u
            where (:equipoId is null or u.equipo.id = :equipoId)
              and (:rolId    is null or u.rol.id    = :rolId)
              and (:cargoId  is null or u.cargo.id  = :cargoId)
            """,
            countQuery = """
            select count(u) from Usuario u
            where (:equipoId is null or u.equipo.id = :equipoId)
              and (:rolId    is null or u.rol.id    = :rolId)
              and (:cargoId  is null or u.cargo.id  = :cargoId)
            """
    )
    Page<Usuario> findByFilters(@Param("equipoId") Long equipoId,
                                @Param("rolId") Long rolId,
                                @Param("cargoId") Long cargoId,
                                Pageable pageable);
}

