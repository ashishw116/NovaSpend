package dev.ashishwagh.novaspend.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import dev.ashishwagh.novaspend.exception.InvalidTokenException;
import dev.ashishwagh.novaspend.model.RefreshToken;
import dev.ashishwagh.novaspend.repository.RefreshTokenRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService{
	private final RefreshTokenRepo refreshTokenRepo;
	private static final long Ref_Token_Duration=7*24*60*60;
	@Override
	public RefreshToken createRefreshToken(String userId)
	{
		refreshTokenRepo.deleteByUserId(userId);
		RefreshToken refreshToken=new RefreshToken();
		refreshToken.setUserId(userId);
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setExpiryDate(Instant.now().plusSeconds(Ref_Token_Duration));
		return refreshTokenRepo.save(refreshToken);
	}
	@Override
	public RefreshToken verifyExpiration(RefreshToken token) {
		if(token.getExpiryDate().isBefore(Instant.now()))
		{
			refreshTokenRepo.delete(token);
			throw new InvalidTokenException("Refresh token expired");
		}
		return token;
	}
	@Override
	public void deleteByUserId(String userId) {
		refreshTokenRepo.deleteByUserId(userId);
		
	}
	@Override
	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepo.findByToken(token);
	}
}
