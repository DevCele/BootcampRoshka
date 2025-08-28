package com.roshka.vacas.controller;

import com.roshka.vacas.dto.*;
import com.roshka.vacas.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    private final UsuarioService service;
    public UsuarioController(UsuarioService service) { this.service = service; }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse crear(@RequestBody @Valid UsuarioCreateRequest req) {
        return service.crear(req);
    }

    @GetMapping("/{id}")
    public UsuarioResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PutMapping("/{id}")
    public UsuarioResponse actualizar(@PathVariable Long id, @RequestBody @Valid UsuarioCreateRequest req) {
        return service.actualizar(id, req);
    }

    @PatchMapping("/{id}")
    public UsuarioResponse patch(@PathVariable Long id, @RequestBody UsuarioPatchRequest req) {
        return service.patch(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping(produces = "application/json")
    public Page<UsuarioResponse> listar(
            @RequestParam(required = false) Long equipoId,
            @RequestParam(required = false) Long rolId,
            @RequestParam(required = false) Long cargoId,
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC)
            Pageable pageable
    ) {
        return service.listar(equipoId, rolId, cargoId, pageable);
    }
}
