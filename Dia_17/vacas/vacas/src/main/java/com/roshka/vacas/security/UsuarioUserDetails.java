package com.roshka.vacas.security;

import com.roshka.vacas.entity.Usuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

//  roles/authorities reales se mapean aquí.
public class UsuarioUserDetails implements UserDetails {
    private final Usuario usuario;

    public UsuarioUserDetails(Usuario usuario) { this.usuario = usuario; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(); // mapear roles si los necesitas
    }

    @Override
    public String getPassword() { return usuario.getContrasena(); }

    @Override
    public String getUsername() { return usuario.getCorreo(); }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return usuario.getEstado() != null ? usuario.getEstado() : true;
    }

    public Usuario getUsuario() { return usuario; }
}
