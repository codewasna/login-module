package com.sharenotebook.configuration.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final HandlerExceptionResolver handlerExceptionResolver;

	public JwtAuthorizationFilter(JwtUtil jwtUtil, HandlerExceptionResolver handlerExceptionResolver) {
		this.jwtUtil = jwtUtil;
		this.handlerExceptionResolver = handlerExceptionResolver;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			String accessToken = jwtUtil.resolveToken(request);
			if (accessToken == null) {
				filterChain.doFilter(request, response);
				return;
			}

			Claims claims = jwtUtil.resolveClaims(request);

			if (claims.get("roles") == null) {
				throw new RuntimeException("Roles cannot be null");
			}

			@SuppressWarnings("unchecked")
			List<SimpleGrantedAuthority> authorities = ((Collection<String>) claims.get("roles")).stream()
					.map(e -> new SimpleGrantedAuthority(e)).toList();

			if (claims != null & jwtUtil.validateClaims(claims)) {
				String email = claims.getSubject();
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, "", authorities);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

			filterChain.doFilter(request, response);

		} catch (Exception ex) {
			handlerExceptionResolver.resolveException(request, response, null, ex);
		}
	}
}
