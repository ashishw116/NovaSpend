package dev.ashishwagh.novaspend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import dev.ashishwagh.novaspend.service.FinanceEntryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users/{userId}/finance")
public class FinanceEntryController {
	@Autowired
	private FinanceEntryService financeEntryService;
	
	@GetMapping
	public ResponseEntity<List<FinanceEntryResponse>> getAllEntrys(@PathVariable String userId)
	{
		List<FinanceEntryResponse> financeEntries=financeEntryService.getAllEntries(userId);
		return new ResponseEntity<>(financeEntries,HttpStatus.OK);
	}
	@GetMapping("/{entryId}")
	public ResponseEntity<FinanceEntryResponse> getEntryById(@PathVariable String entryId,@PathVariable String userId)
	{
		FinanceEntryResponse financeEntry=financeEntryService.getEntry(entryId,userId);
		return new ResponseEntity<>(financeEntry,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<FinanceEntryResponse> addEntry(@Valid @RequestBody FinanceEntryRequest financeEntryRequest,@PathVariable String userId)
	{
		FinanceEntryResponse financeEntry=financeEntryService.createEntry(financeEntryRequest,userId);
		return new ResponseEntity<>(financeEntry,HttpStatus.CREATED);
	}
	@PutMapping("/{entryId}")
	public ResponseEntity<FinanceEntryResponse> updateEntry(@PathVariable String entryId,@Valid @RequestBody FinanceEntryRequest financeEntryRequest,@PathVariable String userId)
	{
		
		FinanceEntryResponse financeEntry=financeEntryService.updateEntry(entryId,financeEntryRequest,userId);
		return new ResponseEntity<>(financeEntry,HttpStatus.OK);
	}
	@DeleteMapping("/{entryId}")
	public ResponseEntity<?> deleteEntry(@PathVariable String entryId,@PathVariable String userId)
	{
		
		financeEntryService.deleteEntry(entryId,userId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
