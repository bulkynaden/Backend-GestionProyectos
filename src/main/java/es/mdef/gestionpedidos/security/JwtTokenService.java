package es.mdef.gestionpedidos.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {
    private final static int tokenExpirationTime = 30 * 60 * 1000;
    private final static String tokenKey = "ut1FfO9sSPjG1OKxVh";

    public static String generateToken(UserDetails userDetails, Claims claims) {
        List<String> authorities = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        claims.put("authorities", authorities);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, tokenKey)
                .compact();
    }

    public static void verifyToken(String token) throws JwtException {
        Jwts.parser()
                .setSigningKey(tokenKey)
                .parse(token.substring(7));
    }

    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(tokenKey)
                .parseClaimsJws(token.substring(7))
                .getBody();
    }

    public static String updateExpirationDateToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(SignatureAlgorithm.HS256, tokenKey)
                .compact();
    }
}
