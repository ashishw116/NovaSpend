package dev.ashishwagh.novaspend.service;

import java.util.List;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;

public interface FinanceEntryService {
	FinanceEntryResponse createEntry(FinanceEntryRequest financeEntryRequest);
	FinanceEntryResponse getEntry(String id);
	List<FinanceEntryResponse> getAllEntries();
	FinanceEntryResponse updateEntry(String id,FinanceEntryRequest financeEntryRequest);
	void deleteEntry(String id);
}
