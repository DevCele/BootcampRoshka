package com.roshka.vacas.dto;

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
        String antiguedadIso,
        String antiguedadTexto
) {}
