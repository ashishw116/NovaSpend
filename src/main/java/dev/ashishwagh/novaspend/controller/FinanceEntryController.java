package dev.ashishwagh.novaspend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.ashishwagh.novaspend.dto.FinanceEntryRequest;
import dev.ashishwagh.novaspend.dto.FinanceEntryResponse;
import dev.ashishwagh.novaspend.response.ApiResponse;
import dev.ashishwagh.novaspend.service.FinanceEntryService;
import dev.ashishwagh.novaspend.utility.SecurityUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/finance")
@AllArgsConstructor
public class FinanceEntryController {
	private final FinanceEntryService financeEntryService;
	private final SecurityUtil util;
	@GetMapping
	public ResponseEntity<ApiResponse<List<FinanceEntryResponse>>> getAllEntries(Authentication authentication)
	{
        String userId = util.getUserId(authentication);
		List<FinanceEntryResponse> financeEntries=financeEntryService.getAllEntries(userId);
		return ResponseEntity.ok(ApiResponse.success("Entries Fetched Successfully",financeEntries));
	}
	@GetMapping("/{entryId}")
	public ResponseEntity<ApiResponse<FinanceEntryResponse>> getEntryById(@PathVariable String entryId,Authentication authentication)
	{
		String userId = util.getUserId(authentication);
		FinanceEntryResponse financeEntry=financeEntryService.getEntry(entryId,userId);
		return ResponseEntity.ok(ApiResponse.success("Entry Fetched Successfully",financeEntry));
	}
	
	@PostMapping
	public ResponseEntity<ApiResponse<FinanceEntryResponse>> addEntry(@Valid @RequestBody FinanceEntryRequest financeEntryRequest,Authentication authentication)
	{
		String userId = util.getUserId(authentication);
		FinanceEntryResponse financeEntry=financeEntryService.createEntry(financeEntryRequest,userId);
		return new ResponseEntity<>(ApiResponse.success("Entry Created Successfully",financeEntry),HttpStatus.CREATED);
	}
	@PutMapping("/{entryId}")
	public ResponseEntity<ApiResponse<FinanceEntryResponse>> updateEntry(@PathVariable String entryId,@Valid @RequestBody FinanceEntryRequest financeEntryRequest,Authentication authentication)
	{
		String userId = util.getUserId(authentication);
		FinanceEntryResponse financeEntry=financeEntryService.updateEntry(entryId,financeEntryRequest,userId);
		return ResponseEntity.ok(ApiResponse.success("Entry Updated Successfully",financeEntry));
	}
	@DeleteMapping("/{entryId}")
	public ResponseEntity<ApiResponse<Void>> deleteEntry(@PathVariable String entryId,Authentication authentication)
	{
		String userId = util.getUserId(authentication);
		financeEntryService.deleteEntry(entryId,userId);
		return ResponseEntity.ok(ApiResponse.success("Entry Deleted Successfully"));
	}
}
