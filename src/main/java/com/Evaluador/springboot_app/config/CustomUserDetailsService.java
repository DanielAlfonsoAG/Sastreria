package com.Evaluador.springboot_app.config;

import com.Evaluador.springboot_app.model.Usuario;
import com.Evaluador.springboot_app.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Optional<Usuario> optionalUser = usuarioRepository.findByCorreo(correo);

        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo);
        }

        Usuario usuario = optionalUser.get();

        return User.builder()
                .username(usuario.getCorreo()) // Aquí se usa el correo como nombre de usuario
                .password(usuario.getContrasenia()) // Asegúrate que esté cifrada con BCrypt
                .roles(usuario.getRol()) // El campo rol debe existir y tener valores válidos
                .build();
    }
}
