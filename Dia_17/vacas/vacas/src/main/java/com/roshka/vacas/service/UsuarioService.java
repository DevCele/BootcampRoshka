package com.roshka.vacas.service;

import com.roshka.vacas.dto.UsuarioCreateRequest;
import com.roshka.vacas.dto.UsuarioResponse;
import com.roshka.vacas.entity.Cargo;
import com.roshka.vacas.entity.Equipo;
import com.roshka.vacas.entity.Rol;
import com.roshka.vacas.entity.Usuario;
import com.roshka.vacas.repository.CargoRepository;
import com.roshka.vacas.repository.EquipoRepository;
import com.roshka.vacas.repository.RolRepository;
import com.roshka.vacas.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import com.roshka.vacas.config.SecurityBeans;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final EquipoRepository equipoRepo;
    private final CargoRepository cargoRepo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepo,
                          RolRepository rolRepo,
                          EquipoRepository equipoRepo,
                          CargoRepository cargoRepo,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.equipoRepo = equipoRepo;
        this.cargoRepo = cargoRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponse crear(UsuarioCreateRequest req) {
        validarFechaIngreso(req.fechaIngreso());

        Usuario u = Usuario.builder()
                .nombre(req.nombre())
                .apellido(req.apellido())
                .nroCedula(req.nroCedula())
                .correo(req.correo())
                .rol(    req.rolId()    == null ? null : rolRepo.findById(req.rolId()).orElse(null))
                .equipo( req.equipoId() == null ? null : equipoRepo.findById(req.equipoId()).orElse(null))
                .cargo(  req.cargoId()  == null ? null : cargoRepo.findById(req.cargoId()).orElse(null))
                .fechaIngreso(req.fechaIngreso())
                // NO seteamos antigüedad: se calcula al responder
                .diasVacaciones(req.diasVacaciones())
                .estado(req.estado())
                .telefono(req.telefono())
                .fechaNacimiento(req.fechaNacimiento())
                .diasVacacionesRestante(req.diasVacacionesRestante())
                .requiereCambioContrasena(req.requiereCambioContrasena())
                .build();
        u.setContrasena(passwordEncoder.encode(req.contrasena()));
        u = usuarioRepo.save(u);
        return toResponse(u);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        return usuarioRepo.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponse obtener(Long id) {
        Usuario u = usuarioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        return toResponse(u);
    }

    public UsuarioResponse actualizar(Long id, UsuarioCreateRequest req) {
        validarFechaIngreso(req.fechaIngreso());

        Usuario u = usuarioRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        u.setNombre(req.nombre());
        u.setApellido(req.apellido());
        u.setNroCedula(req.nroCedula());
        u.setCorreo(req.correo());

        Rol rol = (req.rolId() == null) ? null : rolRepo.findById(req.rolId()).orElse(null);
        Equipo equipo = (req.equipoId() == null) ? null : equipoRepo.findById(req.equipoId()).orElse(null);
        Cargo cargo = (req.cargoId() == null) ? null : cargoRepo.findById(req.cargoId()).orElse(null);

        u.setRol(rol);
        u.setEquipo(equipo);
        u.setCargo(cargo);

        u.setFechaIngreso(req.fechaIngreso());
        u.setDiasVacaciones(req.diasVacaciones());
        u.setEstado(req.estado());
        u.setTelefono(req.telefono());
        u.setFechaNacimiento(req.fechaNacimiento());
        u.setDiasVacacionesRestante(req.diasVacacionesRestante());
        u.setRequiereCambioContrasena(req.requiereCambioContrasena());

        if (req.contrasena() != null && !req.contrasena().isBlank()) {
            u.setContrasena(passwordEncoder.encode(req.contrasena()));
        }

        u = usuarioRepo.save(u);
        return toResponse(u);
    }

    public void eliminar(Long id) {
        if (!usuarioRepo.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuarioRepo.deleteById(id);
    }

    // ---------------- helpers ----------------

    private void validarFechaIngreso(LocalDate fecha) {
        if (fecha != null && fecha.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("fechaIngreso no puede ser futura");
        }
    }

    private UsuarioResponse toResponse(Usuario u) {
        // Calcular antigüedad "on the fly"
        Period antig = (u.getFechaIngreso() == null)
                ? null
                : Period.between(u.getFechaIngreso(), LocalDate.now());

        return new UsuarioResponse(
                u.getId(),
                u.getNombre(),
                u.getApellido(),
                u.getNroCedula(),
                u.getCorreo(),
                u.getRol() == null ? null : u.getRol().getId(),
                u.getEquipo() == null ? null : u.getEquipo().getId(),
                u.getCargo() == null ? null : u.getCargo().getId(),
                u.getEstado(),
                antig == null ? null : antig.toString(),  // ISO: PnYnMnD
                pretty(antig)                              // “n años n meses n días”
        );
    }

    private static String pretty(Period p) {
        if (p == null) return null;
        int y = p.getYears();
        int m = p.getMonths();
        int d = p.getDays();
        return String.format("%d años %d meses %d días", y, m, d);
    }
}
