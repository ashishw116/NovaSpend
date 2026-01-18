package dev.ashishwagh.novaspend.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter{
	private final CustomUserDetailsService userDetailsService;
	private final AuthUtil authUtil;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			final String requestTokenHeader=request.getHeader("Authorization");
			String path = request.getRequestURI();
			if (path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs")) {
			    filterChain.doFilter(request, response);
			    return;
			}
			if(requestTokenHeader==null||!requestTokenHeader.startsWith("Bearer "))
			{
				filterChain.doFilter(request, response);
				return;
			}
			String token = requestTokenHeader.substring(7);
			if (authUtil.isTokenExpired(token))
				throw new JwtException("Token expired");
			String email=authUtil.getUserEmail(token);
			if(email!=null&&SecurityContextHolder.getContext().getAuthentication()==null)
			{					
				UserDetails userDetails =userDetailsService.loadUserByUsername(email);
				UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			filterChain.doFilter(request, response);
	}

}
