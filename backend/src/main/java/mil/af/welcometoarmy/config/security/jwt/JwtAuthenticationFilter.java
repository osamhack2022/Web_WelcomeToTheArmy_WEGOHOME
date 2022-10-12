package mil.af.welcometoarmy.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        String requestURI = request.getRequestURI();

        try {
            if (StringUtils.isNotEmpty(jwt) && jwtTokenProvider.validateToken(jwt)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI); }
        } catch (SecurityException | MalformedJwtException e) {
            request.setAttribute("exception", "유효하지 않은 토큰입니다.");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "만료된 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "지원되지 않는 토큰입니다.");
        } catch (SignatureException e) {
            request.setAttribute("exception", "잘못된 토큰 서명입니다.");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "토큰이 잘못되었습니다.");
        } catch (Exception e) {
            logger.error("JwtFilter - doFilter() 오류발생");
            logger.error("token : {}", jwt);
            logger.error("Exception Message : {}", e.getMessage());
            logger.error("Exception StackTrace : {");
            e.printStackTrace();
            logger.error("}");
            request.setAttribute("exception", "알 수 없는 에러가 발생했습니다.");
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.isNotEmpty(bearerToken)) {
            return bearerToken;
        }
        return null;
    }
}
