package tamlagukbe.tamlagukbe.auth.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import tamlagukbe.tamlagukbe.auth.jwt.JwtAccessDeniedHandler;
import tamlagukbe.tamlagukbe.auth.jwt.JwtAuthenticationEntryPoint;
import tamlagukbe.tamlagukbe.auth.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers(requestHasRoleUser()).hasRole("USER")
                                .requestMatchers(requestHasRoleAdmin()).hasRole("ADMIN")
                                .requestMatchers(requestHasAnyRoleUserAdmin()).hasAnyRole("USER", "ADMIN")
                                .anyRequest().permitAll()
                ).exceptionHandling(configurer -> {
                    configurer.authenticationEntryPoint(jwtAuthenticationEntryPoint);
                    configurer.accessDeniedHandler(jwtAccessDeniedHandler);
                })
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    // USER 역할을 가진 사용자에게 허용된 요청 매처를 정의합니다.
    private RequestMatcher[] requestHasRoleUser() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher("/api/v1/member"),
                antMatcher(POST, "/api/v1/post"),
                antMatcher(PATCH, "/api/v1/post"),
                antMatcher(DELETE, "/api/v1/post"),
                antMatcher(POST, "/api/v1/meeting"),
                antMatcher(GET, "/api/v1/meeting")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    // ADMIN 역할을 가진 사용자에게 허용된 요청 매처를 정의합니다.
    private RequestMatcher[] requestHasRoleAdmin() {
        // 현재 ADMIN 역할을 위한 경로가 없다면 빈 배열 반환
        return new RequestMatcher[0];
    }

    // USER 및 ADMIN 역할을 가진 사용자에게 허용된 요청 매처를 정의합니다.
    private RequestMatcher[] requestHasAnyRoleUserAdmin() {
        // 현재 USER 및 ADMIN 역할을 위한 경로가 없다면 빈 배열 반환
        return new RequestMatcher[0];
    }
}
