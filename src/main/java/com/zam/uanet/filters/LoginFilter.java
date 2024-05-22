package com.zam.uanet.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zam.uanet.dtos.LoginResponse;
import com.zam.uanet.dtos.StudentDTO;
import com.zam.uanet.entities.StudentEntity;
import com.zam.uanet.entities.UserEntity;
import com.zam.uanet.services.StudentService;
import com.zam.uanet.services.UserService;
import com.zam.uanet.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collections;

public class LoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    public LoginFilter(AuthenticationManager auth) {
        super("/api/users/login");
        setAuthenticationManager(auth);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        System.out.println("Autenticando");
        try {
            InputStream body = request.getInputStream();
            UserEntity user = new ObjectMapper().readValue(body, UserEntity.class);
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(user.getEmail()
                    , user.getPassword(), Collections.emptyList()));
        } catch (BadCredentialsException e) {
            // Manejar la excepción de credenciales incorrectas
            unsuccessfulAuthentication(request, response, e);
            return null;
        }
    }

    //Este metodo se ejecuta automaticamente por spring security despues de que las credenciales del usuario sean correctas
    @Override
    public void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        System.out.println("Las credenciales del usuario son correctos!");

        // Ejecutar lógica adicional después de la autenticación exitosa
        LoginResponse loginResponse = accionLogin(auth);

        // Agregar el token al encabezado de la respuesta utilizando JwtUtil
        JwtUtil.addAuthentication(res, auth.getName());

        // Devolver el objeto cliente en el cuerpo de la respuesta
        res.setContentType("application/json");
        PrintWriter writer = res.getWriter();
        writer.println(new ObjectMapper().writeValueAsString(loginResponse));
        writer.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("Las credenciales son incorrectas");
        // Configurar la respuesta en caso de fallo de autenticación
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Error de autenticacion: Credenciales invalidas");
    }

    private LoginResponse accionLogin(Authentication auth) {
        UserEntity user = userService.listUser().stream().filter(u->u.getEmail().equals(auth.getName())).findFirst().orElse(null);
        StudentDTO studentDTO = studentService.findByUserQuery(user.getIdUser());
        LoginResponse loginResponse = new LoginResponse(true, "Autenticación exitosa", studentDTO);
        return loginResponse;
    }
}
