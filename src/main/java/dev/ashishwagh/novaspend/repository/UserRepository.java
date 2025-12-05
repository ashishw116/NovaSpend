package dev.ashishwagh.novaspend.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.ashishwagh.novaspend.model.User;

public interface UserRepository extends MongoRepository<User, String>{
	Optional<User> findByEmail(String email);
	
}
