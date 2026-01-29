package com.lb.configrations;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidator extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		if (jwt == null || !jwt.startsWith("Bearer ")) {
		    filterChain.doFilter(request, response);
		    return;
		}

		jwt = jwt.substring(7);
		try {
			SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
			Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();
			
			String email=String.valueOf(claims.get("email"));

			String authories = String.valueOf(claims.get("authorities"));
			List<GrantedAuthority> authotitiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authories);
			Authentication auth = new UsernamePasswordAuthenticationToken(email, null, authotitiesList);
			SecurityContextHolder.getContext().setAuthentication(auth);
			
		} catch (Exception e) {
			throw new BadCredentialsException("Invalid JWT token!");

		}
		filterChain.doFilter(request, response);
	}
}
