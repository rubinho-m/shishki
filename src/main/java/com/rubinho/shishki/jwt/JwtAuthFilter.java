package com.rubinho.shishki.jwt;

import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserAuthProvider userAuthProvider;
    private final JwtUtils jwtUtils;

    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver resolver;

    public JwtAuthFilter(UserAuthProvider userAuthProvider,
                         HandlerExceptionResolver resolver,
                         JwtUtils jwtUtils) {
        this.userAuthProvider = userAuthProvider;
        this.resolver = resolver;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null) {
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                String token = jwtUtils.escapeToken(bearerToken);
                try {
                    SecurityContextHolder.getContext().setAuthentication(
                            userAuthProvider.validateToken(token)
                    );
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    resolver.resolveException(request, response, null, e);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}