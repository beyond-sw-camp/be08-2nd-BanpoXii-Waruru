package waruru.backend.member.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import waruru.backend.member.config.JwtTokenProvider;
import waruru.backend.member.domain.RefreshToken;
import waruru.backend.member.domain.RefreshTokenRepository;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Optional<Cookie> accessTokenCookie = jwtTokenProvider.resolveAccessToken(request);
        System.out.println(accessTokenCookie);

        if (accessTokenCookie.isPresent()) {
            String accessToken = accessTokenCookie.get().getValue();
            boolean isValid = jwtTokenProvider.validateToken(accessToken);
            Authentication authentication;

            if (isValid){
                authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                RefreshToken refreshToken = refreshTokenRepository.findByAccessToken(accessToken).orElseThrow(() ->
                        new RuntimeException("Refresh token not found"));
                if(refreshToken != null) {
                    String userId = refreshToken.getUsername();
                    String newAccessToken = jwtTokenProvider.reIssueAccessToken(userId);
                    refreshToken.setAccessToken(newAccessToken);
                    refreshTokenRepository.save(refreshToken);
                    authentication = jwtTokenProvider.getAuthentication(newAccessToken);

                    Cookie cookie = new Cookie("access_token", newAccessToken);
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    cookie.setPath("/");
                    cookie.setMaxAge(3600);

                    response.addCookie(cookie);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
