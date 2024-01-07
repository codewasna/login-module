package com.sharenotebook.configuration.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtil {

	private final String secret_key = "codewasna_sharenotebook";
	private long accessTokenValidity = 60;

	private final JwtParser jwtParser;

	private final String TOKEN_HEADER = "Authorization";
	private final String TOKEN_PREFIX = "Bearer ";

	public JwtUtil() {
		this.jwtParser = Jwts.parser().setSigningKey(secret_key);
	}

	public String createToken(User user) {
		Claims claims = Jwts.claims().setSubject(user.getUsername());
		claims.put("roles", user.getAuthorities().stream().map(e -> e.getAuthority()).toList());

		Date tokenCreateTime = new Date();
		Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.MINUTES.toMillis(accessTokenValidity));

		return Jwts.builder().setClaims(claims).setExpiration(tokenValidity)
				.signWith(SignatureAlgorithm.HS256, secret_key).compact();
	}

	private Claims parseJwtClaims(String token) {
		return jwtParser.parseClaimsJws(token).getBody();
	}

	public Claims resolveClaims(HttpServletRequest req) {
		String token = resolveToken(req);
		if (token != null) {
			return parseJwtClaims(token);
		}
		return null;
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(TOKEN_HEADER);
		if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
			return bearerToken.substring(TOKEN_PREFIX.length());
		}
		return null;
	}

	public boolean validateClaims(Claims claims) throws AuthenticationException {
		return claims.getExpiration().after(new Date());
	}

	public String getEmail(Claims claims) {
		return claims.getSubject();
	}

}
