package com.vetcode.BrilloconnetzJavaDeveloperTest.jwt;

import com.vetcode.BrilloconnetzJavaDeveloperTest.dto.UserInputDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Slf4j
@Component
public class JwtService {

    private final String jwtSecret = "JWTSecret";
    private final long jwtExpiration = 604800000;


    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpiration);
        System.out.println(jwtSecret);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();

    }

    public String generateTokenForValidatedUser(UserInputDto validatedUser) {
        UserDetails userDetails = createUserDetailsFromUserInput(validatedUser);
        return generateToken(userDetails);
    }

    public String verifyToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return "verification pass";
        } catch (Exception e) {
            return "verification fails";
        }
    }

    private UserDetails createUserDetailsFromUserInput(UserInputDto validatedUser) {
        String username = validatedUser.getUsername();
        String password = validatedUser.getPassword();
        String email = validatedUser.getEmail();
        Date dateOfBirth = validatedUser.getDateOfBirth();
        Collection<? extends GrantedAuthority> authorities = new ArrayList<>();

        return new CustomUserDetails(username, password, email, dateOfBirth, authorities);
    }
}
