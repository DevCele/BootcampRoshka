package com.roshka.vacas.controller;

import com.roshka.vacas.dto.AuthRequest;
import com.roshka.vacas.dto.AuthResponse;
import com.roshka.vacas.dto.RegisterRequest;
import com.roshka.vacas.entity.Cargo;
import com.roshka.vacas.entity.Equipo;
import com.roshka.vacas.entity.Rol;
import com.roshka.vacas.entity.Usuario;
import com.roshka.vacas.repository.CargoRepository;
import com.roshka.vacas.repository.EquipoRepository;
import com.roshka.vacas.repository.RolRepository;
import com.roshka.vacas.repository.UsuarioRepository;
import com.roshka.vacas.security.JwtService;
import com.roshka.vacas.security.UsuarioDetailsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authManager;
    private final UsuarioDetailsService usuarioDetailsService;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository;
    private final CargoRepository cargoRepository;
    private final EquipoRepository equipoRepository;

    public AuthController(AuthenticationManager authManager,
                          UsuarioDetailsService usuarioDetailsService,
                          JwtService jwtService,
                          UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          RolRepository rolRepository,
                          CargoRepository cargoRepository,
                          EquipoRepository equipoRepository) {
        this.authManager = authManager;
        this.usuarioDetailsService = usuarioDetailsService;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
        this.cargoRepository = cargoRepository;
        this.equipoRepository = equipoRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest req) {
        var authentication = new UsernamePasswordAuthenticationToken(
                req.correo(), req.contrasena()
        );
        authManager.authenticate(authentication);

        Usuario u = usuarioRepository.findWithDetailsByCorreo(req.correo())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String token = jwtService.generateToken(u);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {

        usuarioRepository.findByCorreo(req.correo()).ifPresent(u -> {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo");
        });

        Rol rol = rolRepository.findById(req.rolId())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        Cargo cargo = cargoRepository.findById(req.cargoId())
                .orElseThrow(() -> new IllegalArgumentException("Cargo no encontrado"));

        Equipo equipo = equipoRepository.findById(req.equipoId())
                .orElseThrow(() -> new IllegalArgumentException("Equipo no encontrado"));


        Usuario u = Usuario.builder()
                .nombre(req.nombre())
                .apellido(req.apellido())
                .correo(req.correo())
                .telefono(req.telefono())
                .nroCedula(req.nroCedula())
                .estado(true)
                .diasVacaciones(0)
                .diasVacacionesRestante(0)
                .requiereCambioContrasena(false)
                .rol(rol)
                .cargo(cargo)
                .equipo(equipo)
                .fechaIngreso(req.fechaIngreso())
                .build();
        u.setContrasena(passwordEncoder.encode(req.contrasena()));
        u = usuarioRepository.save(u);

        Usuario uConDetalle = usuarioRepository.findWithDetailsByCorreo(u.getCorreo())
                .orElseThrow(() -> new RuntimeException("Error al cargar el usuario luego del registro"));
        String token = jwtService.generateToken(uConDetalle);
        return ResponseEntity.ok(new AuthResponse(token));
    }
    

}
