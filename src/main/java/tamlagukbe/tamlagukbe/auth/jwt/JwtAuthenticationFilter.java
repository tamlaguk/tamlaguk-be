package tamlagukbe.tamlagukbe.auth.jwt;

import static tamlagukbe.tamlagukbe.global.exception.type.ErrorCode.UNKNOWN_ERROR;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import tamlagukbe.tamlagukbe.global.exception.GlobalException;

/**
 * 요청 헤더에서 JWT 토큰을 추출하고, 토큰의 유효성 검사
 * 토큰이 유효하면 SecurityContext 에 인증 정보를 저장
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Value("${spring.jwt.prefix}")
    private String tokenPrefix;

    @Value("${spring.jwt.header}")
    private String tokenHeader;

    /**
     * HTTP 요청을 필터링 하여 JWT 토큰을 검증하고, 유효한 토큰일 경우 인증 정보를 SecurityContext 에 저장.
     * 만약 토큰이 유효하지 않다면 예외 처리
     *
     * @param request     HTTP 요청
     * @param response    HTTP 응답
     * @param filterChain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException      입출력 예외
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // HTTP 요청 헤더에서 JWT 토큰을 추출
        String token = resolveTokenFromRequest(request);

        try {
            // 토큰이 존재하고 유효한 경우
            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                // 토큰으로부터 인증 정보를 받아옴
                Authentication authentication = tokenProvider.getAuthentication(token);
                log.info("getAuthentication method called with token: {}", token);

                // SecurityContext 에 인증 정보를 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("Authentication object stored in SecurityContext: {}", authentication);
            }
        } catch (GlobalException e) {
            // 예외 발생 시, 예외를 요청 속성에 저장
            request.setAttribute("exception", UNKNOWN_ERROR);
        }

        // 필터 체인 계속 실행
        filterChain.doFilter(request, response);
    }

    /**
     * HTTP 요청 헤더에서 JWT 토큰 추출하여 반환.
     *
     * @param request HTTP 요청
     * @return 추출한 JWT 토큰 or null
     */
    private String resolveTokenFromRequest(HttpServletRequest request) {
        // 요청 헤더에서 토큰을 추출
        String token = request.getHeader(this.tokenHeader);
        // 토큰이 존재하고, 지정된 접두사로 시작하는 경우
        if (!ObjectUtils.isEmpty(token) && token.startsWith(this.tokenPrefix)) {
            // 접두사를 제거한 토큰을 반환
            return token.substring(this.tokenPrefix.length());
        }
        // 토큰이 없거나 접두사로 시작하지 않으면 null 반환
        return null;
    }
}
