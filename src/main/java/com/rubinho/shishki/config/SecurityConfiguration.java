package com.rubinho.shishki.config;

import com.rubinho.shishki.jwt.JwtAccessDeniedHandler;
import com.rubinho.shishki.jwt.JwtAuthFilter;
import com.rubinho.shishki.jwt.JwtAuthenticationEntryPoint;
import com.rubinho.shishki.jwt.UserAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserAuthProvider userAuthProvider;

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;

    @Autowired
    public SecurityConfiguration(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                 UserAuthProvider userAuthProvider,
                                 @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userAuthProvider = userAuthProvider;
        this.resolver = resolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new JwtAuthFilter(userAuthProvider, resolver), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exception) -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/api/v*/admin").hasRole("ADMIN")
                        .requestMatchers("/api/v*/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v*/bookings").hasRole("ADMIN")

                        .requestMatchers("/api/v*/register").permitAll()
                        .requestMatchers("/api/v*/login").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v*/glampings/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v*/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/v*/houses/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v*/shop/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v*/services/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v*/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v*/types/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v*/statuses/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/v*/glampings/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/v*/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v*/houses/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/v*/shop/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v*/services/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v*/reviews/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/v*/types/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.POST, "/api/v*/statuses/**").hasAnyRole("ADMIN", "OWNER")

                        .requestMatchers(HttpMethod.PUT, "/api/v*/glampings/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/v*/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v*/houses/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/v*/shop/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v*/services/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v*/reviews/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/v*/types/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/v*/statuses/**").hasAnyRole("ADMIN", "OWNER")

                        .requestMatchers(HttpMethod.DELETE, "/api/v*/glampings/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v*/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v*/houses/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v*/shop/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v*/services/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v*/reviews/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/v*/types/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v*/statuses/**").hasAnyRole("ADMIN", "OWNER")

                        .requestMatchers(HttpMethod.GET, "/api/v*/photo/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v*/photo/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/api/v*/photo/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/api/v*/photo/**").hasAnyRole("ADMIN")

                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new JwtAccessDeniedHandler();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000/");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
