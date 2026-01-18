package dev.ashishwagh.novaspend.service;

import java.util.Optional;

import dev.ashishwagh.novaspend.model.RefreshToken;

public interface RefreshTokenService {

	RefreshToken createRefreshToken(String userId);
	RefreshToken verifyExpiration(RefreshToken token);
	void deleteByUserId(String userId);
	Optional<RefreshToken> findByToken(String token);

}
