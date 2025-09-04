package com.roshka.vacas.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank String nombre,
        @NotBlank String apellido,
        @Email @NotBlank String correo,
        @NotNull Integer nroCedula,
        @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
        @NotBlank String contrasena,
        String telefono,
        @NotNull Long rolId,
        @NotNull Long equipoId,
        @NotNull Long cargoId,
        @NotNull LocalDate fechaIngreso
        ) {}
