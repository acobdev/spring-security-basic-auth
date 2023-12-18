package dev.acobano.springsecurity.basicauth.modelo.entidades;

import dev.acobano.springsecurity.basicauth.modelo.enumerados.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Null;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter @Setter
public class Usuario implements UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    @Null
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private Rol rol;


    //Permisos concedidos:
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<SimpleGrantedAuthority> permisos = new java.util.ArrayList<>(rol.getListaPermisos().stream()
                .map(permiso -> new SimpleGrantedAuthority((permiso.name())))
                .toList());

        permisos.add(new SimpleGrantedAuthority("ROLE_" + rol.name()));

        return permisos;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
