package dev.ashishwagh.novaspend.service;

import java.time.LocalDate;
import java.util.List;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponseProjection;
import dev.ashishwagh.novaspend.dto.PageResponse;
import dev.ashishwagh.novaspend.model.EntryType;

public interface FinanceEntryService {
	FinanceEntryResponse createEntry(FinanceEntryRequest financeEntryRequest,String userId);
	FinanceEntryResponse getEntry(String id,String userId);
	FinanceEntryResponse updateEntry(String id,FinanceEntryRequest financeEntryRequest,String userId);
	void deleteEntry(String id,String userId);
	PageResponse<FinanceEntryResponseProjection> getFilterEntries(String userId, int page, int size, EntryType type,
			List<String> categories, LocalDate fromDate, LocalDate toDate);
}
