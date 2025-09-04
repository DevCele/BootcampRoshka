package com.roshka.vacas.dto;

import java.time.LocalDate;

public record UsuarioResponse(
        Long id,
        String nombre,
        String apellido,
        Integer nroCedula,
        String correo,
        Long rolId,
        Long equipoId,
        Long cargoId,
        Boolean estado,
        String antiguedad,
        String antiguedadPretty,
        String telefono,
        LocalDate fechaIngreso,
        LocalDate fechaNacimiento,
        Integer diasVacaciones,
        Integer diasVacacionesRestante,
        Boolean requiereCambioContrasena
) {}
