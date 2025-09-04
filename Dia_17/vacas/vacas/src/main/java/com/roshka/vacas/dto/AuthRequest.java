package com.roshka.vacas.dto;

public record AuthRequest(
        String correo,
        String contrasena
) { }
