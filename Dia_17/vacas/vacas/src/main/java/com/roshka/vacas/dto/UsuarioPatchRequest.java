package com.roshka.vacas.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UsuarioPatchRequest(
        String nombre,
        String apellido,
        Integer nroCedula,
        String correo,
        Long rolId,
        Long equipoId,
        Long cargoId,
        LocalDate fechaIngreso,
        Integer diasVacaciones,
        Boolean estado,
        String telefono,
        LocalDate fechaNacimiento,
        Integer diasVacacionesRestante,
        Boolean requiereCambioContrasena,
        String contrasena
) {}
