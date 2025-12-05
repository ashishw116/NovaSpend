package dev.ashishwagh.novaspend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.ashishwagh.novaspend.model.FinanceEntry;

public interface FinanceEntryRepository extends MongoRepository<FinanceEntry,String>{
	List<FinanceEntry> findByUserId(String userId);
}
