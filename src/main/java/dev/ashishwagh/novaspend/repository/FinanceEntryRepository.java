package dev.ashishwagh.novaspend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.ashishwagh.novaspend.model.FinanceEntry;

public interface FinanceEntryRepository extends MongoRepository<FinanceEntry,String>{

}
