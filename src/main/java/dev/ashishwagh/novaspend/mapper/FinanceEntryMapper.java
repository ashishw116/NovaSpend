package dev.ashishwagh.novaspend.mapper;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;
import dev.ashishwagh.novaspend.model.FinanceEntry;

public class FinanceEntryMapper {
	public FinanceEntry toEntity(FinanceEntryRequest financeEntryRequest)
	{
		FinanceEntry financeEntry=new FinanceEntry();
		financeEntry.setAmount(financeEntryRequest.getAmount());
		financeEntry.setCategory(financeEntryRequest.getCategory());
		financeEntry.setDate(financeEntryRequest.getDate());
		financeEntry.setDescription(financeEntryRequest.getDescription());
		financeEntry.setTags(financeEntryRequest.getTags());
		financeEntry.setType(financeEntryRequest.getType());
		return financeEntry;
	}
	public FinanceEntryResponse toResponse(FinanceEntry financeEntry)
	{
		FinanceEntryResponse financeEntryResponse=new FinanceEntryResponse();
		financeEntryResponse.setId(financeEntry.getId());
		financeEntryResponse.setAmount(financeEntry.getAmount());
		financeEntryResponse.setCategory(financeEntry.getCategory());
		financeEntryResponse.setDate(financeEntry.getDate());
		financeEntryResponse.setDescription(financeEntry.getDescription());
		financeEntryResponse.setTags(financeEntry.getTags());
		financeEntryResponse.setType(financeEntry.getType());
		return financeEntryResponse;
	}
}
