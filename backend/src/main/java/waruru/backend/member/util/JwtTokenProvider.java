package waruru.backend.member.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import waruru.backend.member.domain.MemberRole;
import waruru.backend.member.domain.RefreshTokenRepository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Getter
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.header}")
    private String accessHeader;

    // 토큰 유효시간 10분
    @Value("${jwt.access.expiration}")
    private long accessTokenValidTime;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    // 토큰 유효시간 24시간
    @Value("${jwt.refresh.expiration}")
    private long refreshTokenValidTime;

    private final UserDetailsService userDetailsService;

    private final RefreshTokenRepository refreshTokenRepository;

    public String createToken(String sub ,String userPK, long tokenValidTime) {

        Header header = Jwts.header()
                .add("typ", "JWT")
                .build();

        Claims claims = Jwts.claims()
                .add("sub", sub)
                .add("user_id", userPK)
                .build();

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Date now = new Date();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + tokenValidTime * 1000L))
                .issuer("waruru")
                .signWith(key)
                .header().add(header).and()
                .compact();
    }

    public String createAccessToken(String sub, String userPK, MemberRole roles) {

        return this.createToken(accessHeader, userPK, accessTokenValidTime);
    }

    public String createRefreshToken(String sub, String userPK, MemberRole roles) {

        return this.createToken(refreshHeader, userPK, refreshTokenValidTime);
    }

    public Authentication getAuthentication(String token) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPK(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserPK(String token) {

        return Jwts.parser().setSigningKey(secretKey.getBytes()).build().parseSignedClaims(token).getPayload().get("user_id").toString();
    }

    public String getUserRole(String token) {

        return Jwts.parser().setSigningKey(secretKey.getBytes()).build().parseSignedClaims(token).getPayload().get("roles").toString();
    }

    public Optional<Cookie> resolveAccessToken(HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();

        if(cookies != null) {
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("access_token")) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }

    public String reIssueAccessToken(String email){

        return createToken(accessHeader, email, accessTokenValidTime);
    }

    public boolean validateToken(String jwtToken) {

        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey.getBytes()).build().parseSignedClaims(jwtToken);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
