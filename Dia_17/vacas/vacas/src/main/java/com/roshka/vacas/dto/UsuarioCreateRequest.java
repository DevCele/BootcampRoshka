package com.roshka.vacas.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record UsuarioCreateRequest(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @NotNull  Integer nroCedula,
        @Email   String correo,
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
        @NotBlank String contrasena
) {}
