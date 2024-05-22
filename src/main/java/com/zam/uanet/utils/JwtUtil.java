package com.zam.uanet.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.security.Key;
import java.util.Collections;
import java.util.Date;

public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    //Metodo para crear el token
    public static void addAuthentication(HttpServletResponse res, String username) {
        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis()+120000))
                .signWith(SECRET_KEY)
                .compact();
        res.addHeader("Authorization", "Bearer "+token);
    }

    //Metodo para validar el token
    public static Authentication getAuthentication(HttpServletRequest request) {
        String user;
        String token = request.getHeader("Authorization");
        if (token != null) {
            try {
                user = Jwts.parser()
                        .setSigningKey(SECRET_KEY).build()
                        .parseClaimsJws(token.replace("Bearer", "").trim())
                        .getBody()
                        .getSubject();
            } catch (Exception ex) {
                System.out.println("Ocurrio un error al leer el token: "+ex.getMessage());
                user = null;
            }
            return user != null
                    ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList())
                    : null;
        }
        return null;
    }

}
