package waruru.backend.member.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import waruru.backend.member.config.JwtTokenProvider;
import waruru.backend.member.constants.SecurityConstants;
import waruru.backend.member.domain.RefreshTokenRepository;


@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authorization = request.getHeader(SecurityConstants.JWT_HEADER);

        if(authorization == null){
            throw new RuntimeException("please request with access token");
        }

        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        String username = jwtTokenProvider.getUserPK(accessToken);
        refreshTokenRepository.delete(refreshTokenRepository.findByUsername(username));

        response.setStatus(HttpServletResponse.SC_OK);
    }

}
