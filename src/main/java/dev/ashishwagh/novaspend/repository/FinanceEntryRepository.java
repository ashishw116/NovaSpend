package dev.ashishwagh.novaspend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import dev.ashishwagh.novaspend.model.FinanceEntry;

public interface FinanceEntryRepository extends MongoRepository<FinanceEntry,String>,FinanceEntryCustomRepository{
	Page<FinanceEntry> findByUserId(String userId,Pageable pageable);
}
