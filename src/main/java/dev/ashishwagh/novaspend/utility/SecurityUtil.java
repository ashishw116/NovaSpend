package dev.ashishwagh.novaspend.utility;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import dev.ashishwagh.novaspend.model.User;
@Component
public class SecurityUtil
{
	public String getUserId(Authentication authentication) {
	    return ((User) authentication.getPrincipal()).getId();
	}
}
