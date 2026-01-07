package dev.ashishwagh.novaspend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;
import dev.ashishwagh.novaspend.exception.ResourceNotFoundException;
import dev.ashishwagh.novaspend.exception.UnauthorizedAccesException;
import dev.ashishwagh.novaspend.mapper.FinanceEntryMapper;
import dev.ashishwagh.novaspend.model.FinanceEntry;
import dev.ashishwagh.novaspend.repository.FinanceEntryRepository;
@Service
public class FinanceEntryServiceImpl implements FinanceEntryService{

	@Autowired
	FinanceEntryRepository financeEntryRepository;
	@Autowired
	FinanceEntryMapper entryMapper;
	@Override
	public FinanceEntryResponse createEntry(FinanceEntryRequest financeEntryRequest,String userId) {
		FinanceEntry financeEntry=entryMapper.toEntity(financeEntryRequest);
		financeEntry.setUserId(userId);
		FinanceEntry saveEntry=financeEntryRepository.save(financeEntry);
		return entryMapper.toResponse(saveEntry);
	}

	@Override
	public FinanceEntryResponse getEntry(String entryId,String userId) {
		FinanceEntry financeEntry=financeEntryRepository.findById(entryId).orElseThrow(()->new ResourceNotFoundException("Finance entry not found "));
		if(!financeEntry.getUserId().equals(userId))
			throw new UnauthorizedAccesException("You cannot access this entry ");
		return entryMapper.toResponse(financeEntry);
	}

	@Override
	public List<FinanceEntryResponse> getAllEntries(String userId) {
		List<FinanceEntry> financeEntries=financeEntryRepository.findByUserId(userId);
		return financeEntries.stream().map(entryMapper::toResponse).collect(Collectors.toList());
	}

	@Override
	public FinanceEntryResponse updateEntry(String entryId, FinanceEntryRequest financeEntryRequest,String userId) {
		FinanceEntry financeEntry=financeEntryRepository.findById(entryId).orElseThrow(()->new ResourceNotFoundException("Finance entry not found "));
		if(!financeEntry.getUserId().equals(userId))
			throw new UnauthorizedAccesException("You cannot access this entry ");
		financeEntry.setAmount(financeEntryRequest.getAmount());
		financeEntry.setCategory(financeEntryRequest.getCategory());
		financeEntry.setDescription(financeEntryRequest.getDescription());
		financeEntry.setTags(financeEntryRequest.getTags());
		financeEntry.setType(financeEntryRequest.getType());
		financeEntry.setDate(financeEntryRequest.getDate());
		FinanceEntry updated=financeEntryRepository.save(financeEntry);
		return entryMapper.toResponse(updated);
	}

	@Override
	public void deleteEntry(String entryId,String userId) {
		FinanceEntry financeEntry=financeEntryRepository.findById(entryId).orElseThrow(()->new ResourceNotFoundException("Finance entry not found "));
		if(!financeEntry.getUserId().equals(userId))
			throw new UnauthorizedAccesException("You cannot access this entry ");
		financeEntryRepository.deleteById(entryId);
	}

}
