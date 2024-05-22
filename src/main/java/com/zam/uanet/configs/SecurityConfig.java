package com.zam.uanet.configs;

import com.zam.uanet.filters.JwtFilter;
import com.zam.uanet.filters.LoginFilter;
import com.zam.uanet.services.imp.SecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final SecurityServiceImpl securityService;

    @Autowired
    public SecurityConfig(SecurityServiceImpl securityService) {
        this.securityService = securityService;
    }

    //Para encriptar y desincriptar las contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Metodo validar el login con los usuarios de la base de datos
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(securityService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        List<AuthenticationProvider> providers = List.of(authenticationProvider);
        return new ProviderManager(providers);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));//Sin estado
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        (authz) -> {
                            authz.requestMatchers("/api/users/login").permitAll();
                            authz.requestMatchers("/api/posts").permitAll();
                            //authz.requestMatchers("/api/users/**").permitAll();
                            authz.requestMatchers("/api/students/{id}").permitAll();
                            authz.requestMatchers("/api/students").permitAll();
                            //authz.requestMatchers("/api/clientes/registrarse").permitAll();
                            //authz.requestMatchers(HttpMethod.GET,"/api/productos").permitAll();
                            //authz.requestMatchers("/api/**").hasRole("ADMIN");
                            authz.anyRequest().authenticated();
                        }
                ).addFilterBefore(loginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
        return new LoginFilter(authenticationManager);
    }

}
