package waruru.backend.user.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

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
            String username = jwtTokenProvider.getUserPK(accessToken);
            MemberRole role = MemberRole.valueOf(jwtTokenProvider.getUserRole(accessToken));
            RefreshToken refreshToken = refreshTokenRepository.findByUsername(username);

            if(!jwtTokenProvider.validateToken(refreshToken.getRefreshToken())){
                chain.doFilter(request, response);
            }

            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else if (accessToken != null && !jwtTokenProvider.validateToken(accessToken)){
            String username = jwtTokenProvider.getUserPK(accessToken);
            MemberRole role = MemberRole.valueOf(jwtTokenProvider.getUserRole(accessToken));
            RefreshToken refreshToken = refreshTokenRepository.findByUsername(username);

            if(jwtTokenProvider.validateToken(refreshToken.getRefreshToken())){
                accessToken = jwtTokenProvider.createAccessToken(username, role);
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
