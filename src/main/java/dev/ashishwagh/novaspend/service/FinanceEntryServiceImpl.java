package dev.ashishwagh.novaspend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;
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
	public FinanceEntryResponse createEntry(FinanceEntryRequest financeEntryRequest) {
		FinanceEntry financeEntry=entryMapper.toEntity(financeEntryRequest);
		FinanceEntry saveEntry=financeEntryRepository.save(financeEntry);
		return entryMapper.toResponse(saveEntry);
	}

	@Override
	public FinanceEntryResponse getEntry(String id) {
		FinanceEntry financeEntry=financeEntryRepository.findById(id).orElseThrow(()->new RuntimeException("Entry Not Found : "+id));
		return entryMapper.toResponse(financeEntry);
	}

	@Override
	public List<FinanceEntryResponse> getAllEntries() {
		List<FinanceEntry> financeEntries=financeEntryRepository.findAll();
		return financeEntries.stream().map(entryMapper::toResponse).collect(Collectors.toList());
	}

	@Override
	public FinanceEntryResponse updateEntry(String id, FinanceEntryRequest financeEntryRequest) {
		FinanceEntry financeEntry=financeEntryRepository.findById(id).orElseThrow(()->new RuntimeException("Entry Not Found : "+id));
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
	public void deleteEntry(String id) {
		financeEntryRepository.deleteById(id);
	}

}
