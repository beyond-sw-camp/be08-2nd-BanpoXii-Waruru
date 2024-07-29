package waruru.backend.member.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import waruru.backend.member.domain.RefreshToken;
import waruru.backend.member.domain.RefreshTokenRepository;
import waruru.backend.member.util.JwtTokenProvider;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        Optional<Cookie> accessTokenCookie = jwtTokenProvider.resolveAccessToken(request);
        String accessToken = accessTokenCookie.get().getValue();
        RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(accessToken).orElseThrow(() ->
                new IllegalArgumentException("refreshtoken not found"));
        refreshTokenRepository.delete(refreshToken);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
