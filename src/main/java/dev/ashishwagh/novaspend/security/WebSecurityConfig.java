package dev.ashishwagh.novaspend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.ashishwagh.novaspend.exception.JwtAccessDeniedHandler;
import dev.ashishwagh.novaspend.exception.JwtAuthExceptionHandler;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationManager authenticationManager;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthExceptionHandler jwtAuthExceptionHandler;
	private final JWTAuthFilter jwtAuthFilter;
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration configuration
	) throws Exception {
	    return configuration.getAuthenticationManager();
	} 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
	{
		httpSecurity.csrf(csrf->csrf.disable())
		.authorizeHttpRequests(auth-> auth
			.requestMatchers("/auth/**").permitAll()
			.requestMatchers("/finance/**").authenticated()
			.anyRequest().authenticated()
		)
		.exceptionHandling(ex->
			ex.authenticationEntryPoint(jwtAuthExceptionHandler)
			.accessDeniedHandler(jwtAccessDeniedHandler)
		)
		.sessionManagement(session ->
	        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
		.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
		return httpSecurity.build();
	}
}
