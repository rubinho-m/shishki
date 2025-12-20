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
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/bookings").hasRole("ADMIN")

                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/glampings/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.GET, "/houses/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/shop/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/services/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/types/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/statuses/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/glampings/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.POST, "/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/houses/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.POST, "/shop/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/services/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/reviews/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/types/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.POST, "/statuses/**").hasAnyRole("ADMIN", "OWNER")

                        .requestMatchers(HttpMethod.PUT, "/glampings/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/houses/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/shop/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/services/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/reviews/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/types/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/statuses/**").hasAnyRole("ADMIN", "OWNER")

                        .requestMatchers(HttpMethod.DELETE, "/glampings/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/bookings/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/houses/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/shop/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/services/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/reviews/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/types/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/statuses/**").hasAnyRole("ADMIN", "OWNER")

                        .requestMatchers(HttpMethod.GET, "/photo/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/photo/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.PUT, "/photo/**").hasAnyRole("ADMIN", "OWNER")
                        .requestMatchers(HttpMethod.DELETE, "/photo/**").hasAnyRole("ADMIN")

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
