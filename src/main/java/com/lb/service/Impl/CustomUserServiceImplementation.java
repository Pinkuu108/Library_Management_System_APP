package com.lb.service.Impl;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lb.entity.User;
import com.lb.genreRepository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImplementation implements UserDetailsService {


	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found With UserName" + username);
		}
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().toString());
		Collection<? extends GrantedAuthority> authorities=Collections.singletonList(authority);
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	}

}
