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
@RequestMapping("/finance")
public class FinanceEntryController {
	@Autowired
	private FinanceEntryService financeEntryService;
	
	@GetMapping
	public ResponseEntity<List<FinanceEntryResponse>> getAllEntrys()
	{
		List<FinanceEntryResponse> financeEntries=financeEntryService.getAllEntries();
		return new ResponseEntity<>(financeEntries,HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<FinanceEntryResponse> getEntryById(@PathVariable String id)
	{
		FinanceEntryResponse financeEntry=financeEntryService.getEntry(id);
		return new ResponseEntity<>(financeEntry,HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<FinanceEntryResponse> addEntry(@Valid @RequestBody FinanceEntryRequest financeEntryRequest)
	{
		FinanceEntryResponse financeEntry=financeEntryService.createEntry(financeEntryRequest);
		return new ResponseEntity<>(financeEntry,HttpStatus.CREATED);
	}
	@PutMapping("/{id}")
	public ResponseEntity<FinanceEntryResponse> updateEntry(@PathVariable String id,@Valid @RequestBody FinanceEntryRequest financeEntryRequest)
	{
		
		FinanceEntryResponse financeEntry=financeEntryService.updateEntry(id,financeEntryRequest);
		return new ResponseEntity<>(financeEntry,HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteEntry(@PathVariable String id)
	{
		
		financeEntryService.deleteEntry(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
