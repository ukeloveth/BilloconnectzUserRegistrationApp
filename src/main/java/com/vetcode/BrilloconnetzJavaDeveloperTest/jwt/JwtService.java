package com.vetcode.BrilloconnetzJavaDeveloperTest.jwt;

import com.vetcode.BrilloconnetzJavaDeveloperTest.dto.UserInputDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

//@Service
@Configuration
public class JwtService {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpiration;


    public String generateToken(UserDetails userDetails) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtExpiration);

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
