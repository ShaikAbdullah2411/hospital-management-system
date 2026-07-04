package com.hospitalmanagement.DoctorService.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

//    @Autowired
//    private final UserDetailsService userDetailsService;

//    private SecretKey getKey(){
//        return Keys.hmacShaKeyFor(secret.getBytes());
//    }
//    public String generateToken(String username){
//        return Jwts.builder()
//                .subject(username).issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + expiration))
//                .signWith(getKey()).compact();
//    }
//    public String extractUserName(String token){
//
//        return Jwts.parser().verifyWith(getKey())
//                .build().parseSignedClaims(token)
//                .getPayload().getSubject();
//    }
//
//        public boolean validateToken(String token){
//
//            try{
//                Jwts.parser()
//                        .verifyWith(getKey())
//                        .build()
//                        .parseSignedClaims(token);
//                return  true;
//            }
//            catch (JwtException ex){
//                return  false;
//            }
//        } // old code until here

    //new code from here

    public String extractUserName(String token){

        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails) {

            String role = userDetails.getAuthorities().stream()
                    .findFirst().map(GrantedAuthority::getAuthority)
                    .orElse("");
//        return Jwts.builder()
//                .setSubject(userDetails.getUsername())
//                .claim("roles", userDetails.getAuthorities())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() +expiration)) // 1 day
//                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
//                .compact();
        //new code from here
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setClaims(extraClaims)
                .claim("role", role)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +expiration)) // 1 day
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isTokenValid(String token) {
        final String username = extractUserName(token);
        return (username!=null && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }
    public List<String> extractRoles(String token) {

        Claims claims = Jwts.parser()

                .setSigningKey(getSignInKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();

//        return claims.get("role", String.class);
        List<Map<String, String>> rawRoles = claims.get("roles", List.class);
        if (rawRoles == null) {
            return Collections.emptyList();
        }

        return rawRoles.stream()
                .map(roleMap -> roleMap.get("authority"))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefresh(Map<String, Objects> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

    }

    public String getEmailFromToken(String token) {
        return extractUserName(token);
    }

//    public Boolean validateToken(String token) {
//        String userEmail = extractUserName(token);//todo extract userEmail from jwt Token
//        if (StringUtils.isNotEmpty(userEmail) && !isTokenExpired(token)) {
//            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
//            return isTokenValid(token, userDetails);
//        }
//        return false;
//    }
}
