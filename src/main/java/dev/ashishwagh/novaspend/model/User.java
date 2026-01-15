package dev.ashishwagh.novaspend.model;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Document
@Data
public class User implements UserDetails{
	@Id
	private String id;
	private String name;
	private String email;
	private String password;
	private Roles role;
	private Status status;
	private LocalDateTime createdAt;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}
	@Override
	public String getUsername() {
		return this.email;
	}
}
