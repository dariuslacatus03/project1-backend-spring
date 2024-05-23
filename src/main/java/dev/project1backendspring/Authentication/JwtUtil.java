package dev.project1backendspring.Authentication;

import dev.project1backendspring.model.User;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.naming.AuthenticationException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private final SecretKey secretKey;
    private long accessTokenValidity = 60*60*1000;

    private final JwtParser jwtParser;

    private final String TOKEN_HEADER = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    public JwtUtil(){
        this.secretKey = io.jsonwebtoken.security.Keys.secretKeyFor(SignatureAlgorithm.HS256);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getUserName());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims parseJwtClaims(String token)
    {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try
        {
            String token = resolveToken(req);
            if (token != null){
                return parseJwtClaims(token);
            }
            return null;
        }
        catch (ExpiredJwtException ex)
        {
            req.setAttribute("expired", ex.getMessage());
            throw ex;
        }
        catch (Exception ex)
        {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }
    public String resolveToken(HttpServletRequest request)
    {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX))
        {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) throws AuthenticationException {
        try {
            return claims.getExpiration().after(new Date());
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    public String getUsername(Claims claims) {
        return claims.getSubject();
    }

    public List<String> getRoles(Claims claims) {
        return (List<String>) claims.get("roles");
    }
}
