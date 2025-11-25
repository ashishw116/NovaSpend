package dev.ashishwagh.novaspend.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dev.ashishwagh.novaspend.model.EntryType;
import lombok.Data;
@Data
public class FinanceEntryRequest {
	private double amount;
	private String category;
	private LocalDate date;
	private EntryType type;
	private String description;
	private List<String> tags=new ArrayList<>();
}
