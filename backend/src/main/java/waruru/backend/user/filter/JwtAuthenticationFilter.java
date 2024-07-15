package waruru.backend.user.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.OncePerRequestFilter;
import waruru.backend.user.config.JwtTokenProvider;
import waruru.backend.user.domain.MemberRole;
import waruru.backend.user.domain.RefreshToken;
import waruru.backend.user.domain.RefreshTokenRepository;

import java.io.IOException;
import java.util.Date;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        if (accessToken != null && jwtTokenProvider.validateToken(accessToken)) {
            if(!jwtTokenProvider.validateToken(accessToken)){
                Date now = new Date();
                String username = jwtTokenProvider.getUserPK(accessToken);
                MemberRole role = MemberRole.valueOf(jwtTokenProvider.getUserRole(accessToken));
                String refreshToken = jwtTokenProvider.createRefreshToken(username, role);
                RefreshToken newRefreshToken = RefreshToken.builder()
                        .username(username)
                        .refreshToken(refreshToken)
                        .expiration(new Date(now.getTime() + jwtTokenProvider.getRefreshTokenValidTime()).getTime())
                        .build();
                refreshTokenRepository.save(newRefreshToken);
            }
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
