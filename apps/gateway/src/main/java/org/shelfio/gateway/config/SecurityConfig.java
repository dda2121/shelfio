package org.shelfio.gateway.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@EnableRedisHttpSession
public class SecurityConfig {

    /**
     * TODO: CORS and CSRF implementation will be provided later.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.csrf(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/oauth2/**", "/login/oauth2/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults())
                .logout(logout -> logout
                        .deleteCookies("JSESSIONID", "SESSION") // TODO: Remove JSESSIONID after bug is fixed
                        .logoutSuccessHandler((_, res, _) ->
                                res.setStatus(HttpServletResponse.SC_OK)))
                .sessionManagement(session -> session
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::migrateSession));
        return http.build();
    }
}
