package com.roshka.vacas.controller;

import com.roshka.vacas.dto.ItemDto;
import com.roshka.vacas.repository.CargoRepository;
import com.roshka.vacas.repository.EquipoRepository;
import com.roshka.vacas.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@RequiredArgsConstructor
public class CatalogosController {
    private final RolRepository rolRepository;
    private final CargoRepository cargoRepository;
    private final EquipoRepository equipoRepository;

    @GetMapping("/roles")
    public List<ItemDto> roles() {
        return rolRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"))
                .stream()
                .map(r -> new ItemDto(r.getId(), r.getNombre()))
                .toList();
    }

    @GetMapping("/cargos")
    public List<ItemDto> cargos() {
        return cargoRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"))
                .stream()
                .map(c -> new ItemDto(c.getId(), c.getNombre()))
                .toList();
    }

    @GetMapping("/equipos")
    public List<ItemDto> equipos() {
        return equipoRepository.findAll(Sort.by(Sort.Direction.ASC, "nombre"))
                .stream()
                .map(e -> new ItemDto(e.getId(), e.getNombre()))
                .toList();
    }
}
