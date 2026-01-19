package dev.ashishwagh.novaspend.service;

import java.util.List;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;
import dev.ashishwagh.novaspend.dto.PageResponse;

public interface FinanceEntryService {
	FinanceEntryResponse createEntry(FinanceEntryRequest financeEntryRequest,String userId);
	FinanceEntryResponse getEntry(String id,String userId);
	FinanceEntryResponse updateEntry(String id,FinanceEntryRequest financeEntryRequest,String userId);
	void deleteEntry(String id,String userId);
	PageResponse<FinanceEntryResponse> getAllEntries(int page, int size, String userId);
}
