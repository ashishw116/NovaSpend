package dev.ashishwagh.novaspend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.ashishwagh.novaspend.model.RefreshToken;
@Repository
public interface RefreshTokenRepo extends MongoRepository<RefreshToken, String>{
	Optional<RefreshToken> findByToken(String token);
	public void deleteByUserId(String usrId);
}
