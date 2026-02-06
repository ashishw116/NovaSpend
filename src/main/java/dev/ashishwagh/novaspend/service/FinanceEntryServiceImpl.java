package dev.ashishwagh.novaspend.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponseProjection;
import dev.ashishwagh.novaspend.dto.PageResponse;
import dev.ashishwagh.novaspend.exception.ResourceNotFoundException;
import dev.ashishwagh.novaspend.exception.UnauthorizedAccessException;
import dev.ashishwagh.novaspend.mapper.FinanceEntryMapper;
import dev.ashishwagh.novaspend.model.EntryType;
import dev.ashishwagh.novaspend.model.FinanceEntry;
import dev.ashishwagh.novaspend.repository.FinanceEntryRepository;
import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class FinanceEntryServiceImpl implements FinanceEntryService{

	private final FinanceEntryRepository financeEntryRepository;
	private final FinanceEntryMapper entryMapper;
	private final RedisService redisService;
	@Override
	//@CacheEvict(cacheNames = "finance-list",allEntries = true)
	public FinanceEntryResponse createEntry(FinanceEntryRequest financeEntryRequest,String userId) {
		FinanceEntry financeEntry=entryMapper.toEntity(financeEntryRequest);
		financeEntry.setUserId(userId);
		FinanceEntry saveEntry=financeEntryRepository.save(financeEntry);
		redisService.deleteByPattern("finance:"+userId+":*");
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
	//@CacheEvict(cacheNames = "finance-list",allEntries = true)
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
		redisService.deleteByPattern("finance:"+userId+":*");
		return entryMapper.toResponse(updated);
	}

	@Override
	//@CacheEvict(cacheNames = "finance-list",allEntries = true)
	public void deleteEntry(String entryId,String userId) {
		FinanceEntry financeEntry=financeEntryRepository.findById(entryId).orElseThrow(()->new ResourceNotFoundException("Finance entry not found "));
		if(!financeEntry.getUserId().equals(userId))
			throw new UnauthorizedAccessException("You cannot access this entry ");
		financeEntryRepository.deleteById(entryId);
		redisService.deleteByPattern("finance:"+userId+":*");
	}

	
	@Override
	/*@Cacheable(cacheNames = "finance-list",key="""
	#userId+':'+
	#page+':'+
	#size+':'+
	#type+':'+
	#categories+':'+
	#fromDate+':'+
	#toDate
""",unless = "#result.data.isEmpty()")*/
	public PageResponse<FinanceEntryResponseProjection> getFilterEntries(String userId, int page, int size, EntryType type, List<String> categories,LocalDate fromDate,LocalDate toDate) {
		if (page<0) page=0;
	    if (size<=0) size=10;
	    if (size>50) size=50;
	    String key=
	    		"finance:"+userId+":"+
	    		page+":"+
	    		size+":"+
	    		type+":"+
	    		categories+":"+
	    		fromDate+":"+
	    		toDate;
	    PageResponse<FinanceEntryResponseProjection> cahedResponse=redisService.get(key,new TypeReference<PageResponse<FinanceEntryResponseProjection>>() {
		});
	    if(cahedResponse!=null)
	    {
	    	return cahedResponse;
	    }
	    Page<FinanceEntryResponseProjection> financeEntryPage=financeEntryRepository.filterEntries(userId, type, categories, fromDate, toDate, page, size);
	    PageResponse<FinanceEntryResponseProjection> response= new PageResponse<>(
				financeEntryPage.getContent(),
				financeEntryPage.getNumber(),
				financeEntryPage.getSize(),
				financeEntryPage.getTotalElements(),
				financeEntryPage.getTotalPages(),
				financeEntryPage.hasPrevious(),
				financeEntryPage.hasNext()
		);
	    redisService.set(key, response, (long) 5);
	    return response;
	}

}
