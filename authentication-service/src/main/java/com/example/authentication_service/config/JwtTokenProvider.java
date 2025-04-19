package com.example.authentication_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtTokenProvider {

    SecretKey key= Keys.hmacShaKeyFor(JwtValues.secret_key.getBytes());


    public String generateToken(Authentication authentication){

        Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
        String roles=convertingRoles(authorities);
        String jwt= Jwts.builder().setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email",authentication.getName())
                .claim("authorities",roles)
                .signWith(key)
                .compact();
        return jwt;
    }

    public String getEmailFromToken(String jwt){
//        System.out.println("entered to get mail====>");
        jwt=jwt.substring(7);
        Claims claims=Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(jwt).getBody();
//        System.out.println("enter end=============>");
        String email=String.valueOf(claims.get("email"));
        return email;
    }
    public Claims validateToken(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }

    private String convertingRoles(Collection<? extends GrantedAuthority> authorities) {
        Set<String> set=new HashSet<>();

        for(GrantedAuthority auth:authorities){
            set.add(auth.getAuthority());
        }
        return String.join(",",set);
    }

}