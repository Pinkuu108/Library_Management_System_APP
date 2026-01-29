package com.lb.configrations;



import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {

	SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

	public String generateToken(Authentication authentication) {

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

		String roles = populateAuthorities(authorities);

		return Jwts.builder().issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 86400000)) // 24																							// hours
				.claim("email", authentication.getName())
				.claim("authorities", roles)
				.signWith(key)
				.compact();
	}

	public String getEmailFromJwtToken(String jwt) {

	    // Remove "Bearer " prefix safely
	    if (jwt != null && jwt.startsWith("Bearer ")) {
	        jwt = jwt.substring(7);
	    }

	    Claims claims = Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(jwt)
	            .getPayload();

	    return claims.get("email", String.class);
	}

	private String populateAuthorities(
	        Collection<? extends GrantedAuthority> authorities) {

	    Set<String> auths = new HashSet<>();

	    for (GrantedAuthority authority : authorities) {
	        auths.add(authority.getAuthority());
	    }

	    return String.join(",", auths);
	}

}
