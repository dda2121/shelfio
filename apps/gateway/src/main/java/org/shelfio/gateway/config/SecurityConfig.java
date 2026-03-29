package org.shelfio.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
@EnableRedisWebSession
public class SecurityConfig {
    /**
     * TODO
     * [] CSRF implementation
     * [] CORS implementation
     * [] Logout implementation (success handler + delete cookies)
     * [] Session management implementation (session fixation protection).
     *    Check how sessions work in reactive apps.
     */
    @Bean
    public SecurityWebFilterChain webFilterChain(ServerHttpSecurity http) {
        http.csrf(Customizer.withDefaults())
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/oauth2/**", "/login/oauth2/**").permitAll()
                        .anyExchange().authenticated())
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }
}
