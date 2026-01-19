package dev.ashishwagh.novaspend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;
import dev.ashishwagh.novaspend.dto.PageResponse;
import dev.ashishwagh.novaspend.exception.ResourceNotFoundException;
import dev.ashishwagh.novaspend.exception.UnauthorizedAccessException;
import dev.ashishwagh.novaspend.mapper.FinanceEntryMapper;
import dev.ashishwagh.novaspend.model.FinanceEntry;
import dev.ashishwagh.novaspend.repository.FinanceEntryRepository;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class FinanceEntryServiceImpl implements FinanceEntryService{

	private final FinanceEntryRepository financeEntryRepository;
	private final FinanceEntryMapper entryMapper;
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
			throw new UnauthorizedAccessException("You cannot access this entry ");
		return entryMapper.toResponse(financeEntry);
	}

	@Override
	public FinanceEntryResponse updateEntry(String entryId, FinanceEntryRequest financeEntryRequest,String userId) {
		FinanceEntry financeEntry=financeEntryRepository.findById(entryId).orElseThrow(()->new ResourceNotFoundException("Finance entry not found "));
		if(!financeEntry.getUserId().equals(userId))
			throw new UnauthorizedAccessException("You cannot access this entry ");
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
			throw new UnauthorizedAccessException("You cannot access this entry ");
		financeEntryRepository.deleteById(entryId);
	}

	@Override
	public PageResponse<FinanceEntryResponse> getAllEntries(int page, int size,String userId) {
		if(size>50) size=50;
		if(page<0) page=0;
		Pageable pageable= PageRequest.of(page,size,Sort.by(Sort.Direction.DESC,"createdAt"));
		Page<FinanceEntry> financeEntryPage=financeEntryRepository.findByUserId(userId,pageable);
		List<FinanceEntryResponse> responses=financeEntryPage
				.getContent()
				.stream()
				.map(entryMapper::toResponse)
				.toList();
		
		return new PageResponse<>(
				responses,
				financeEntryPage.getNumber(),
				financeEntryPage.getSize(),
				financeEntryPage.getTotalElements(),
				financeEntryPage.getTotalPages(),
				financeEntryPage.hasPrevious(),
				financeEntryPage.hasNext()
			);
	}

}
