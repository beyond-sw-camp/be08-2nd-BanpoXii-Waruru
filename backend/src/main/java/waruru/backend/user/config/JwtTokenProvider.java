package waruru.backend.user.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import waruru.backend.user.constants.SecurityConstants;
import waruru.backend.user.domain.MemberRole;
import waruru.backend.user.domain.RefreshTokenRepository;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Getter
public class JwtTokenProvider {
    //private String secretKey = "63fba97a41e0d004e10e8dbbcb9a547819280efb00a54c732aca36a8a58258e4fcc539ffc5159a7f0a7be78b86efe001c12ba6af6debeb0a89e8ce7e82e75455";

    // 토큰 유효시간 10분
    private final long accessTokenValidTime = 10 * 60 * 1000L;

    // 토큰 유효시간 24시간
    private final long refreshTokenValidTime = 24 * 60 * 60 * 1000L;

    private final UserDetailsService userDetailsService;

    private final RefreshTokenRepository refreshTokenRepository;

//    protected void init() {
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }

    public String createToken(String userPK, MemberRole roles, long tokenValidTime) {
        Claims claims = Jwts.claims().add("roles", roles).add("subject", userPK).build();
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        Date now = new Date();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + tokenValidTime))
                .signWith(key)
                .compact();
    }

    public String createAccessToken(String userPK, MemberRole roles) {
        return this.createToken(userPK, roles, accessTokenValidTime);
    }

    public String createRefreshToken(String userPK, MemberRole roles) {
        return this.createToken(userPK, roles, refreshTokenValidTime);
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPK(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPK(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.JWT_KEY).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public String getUserRole(String token) {
        return Jwts.parser().setSigningKey(SecurityConstants.JWT_KEY).build().parseSignedClaims(token).getPayload().get("roles").toString();
    }

    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader(SecurityConstants.JWT_HEADER);
    }

//    public String resolveRefreshToken(HttpServletRequest request) {
//        return request.getHeader("refreshToken");
//    }
//
    public boolean validateToken(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(SecurityConstants.JWT_KEY).build().parseSignedClaims(jwtToken);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

}
