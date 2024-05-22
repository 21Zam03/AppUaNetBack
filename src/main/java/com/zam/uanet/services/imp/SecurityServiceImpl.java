package com.zam.uanet.services.imp;

import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SecurityServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //Filtramos para obtener el usuario
        UserEntity userEntity = userRepository.findAll().stream().filter(u->u.getEmail().equals(email)).findFirst().orElse(null);
        //Creamos una lista de roles
        List<GrantedAuthority> roles = new ArrayList<>();
        //Logica para añadir roles al usuario
        if (userEntity.getRol().equals("Administrador")) {
            roles.add(new SimpleGrantedAuthority("ROLE_"+"ADMIN"));
        } else {
            roles.add(new SimpleGrantedAuthority("ROLE_"+"USER"));
        }
        /*
            Cuando la encriptacion se maneja sin spring boot
            UserDetails userDetails = new User(usuarioEntity.getNombreUsuario(),"{noop}"+ usuarioEntity.getContrasena(), roles);
        */
        //Cuando la encriptacion se maneja con spring boot
        UserDetails userDetails = new User(userEntity.getEmail(), userEntity.getPassword(), roles);
        return userDetails;
    }


}
