package dev.ashishwagh.novaspend.service;

import java.util.List;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;

public interface FinanceEntryService {
	FinanceEntryResponse createEntry(FinanceEntryRequest financeEntryRequest,String userId);
	FinanceEntryResponse getEntry(String id,String userId);
	List<FinanceEntryResponse> getAllEntries(String userId);
	FinanceEntryResponse updateEntry(String id,FinanceEntryRequest financeEntryRequest,String userId);
	void deleteEntry(String id,String userId);
}
