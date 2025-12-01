package dev.ashishwagh.novaspend.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import dev.ashishwagh.novaspend.model.EntryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class FinanceEntryRequest {
	@Positive(message ="Invalid Amount")
	private double amount; 
	
	@NotBlank(message = "Category is Required")    
	@Size(min=3,max=30,message = "Category must be between 3 and 30 Charectors")
	@Pattern(regexp="^[A-Za-z ]+$", message="Category must contain only letters and spaces")
	private String category;
	
	@NotNull(message = "Date is required")
	@PastOrPresent(message = "Invalid Date")
	private LocalDate date;
	
	@NotNull(message = "Entry type is required")
	private EntryType type;
	
	@Size(max = 200, message = "Description cannot exceed 200 characters")
	private String description;
	
	private List<
	@Size(min = 1, max = 20, message = "Each tag must be 1–20 chars") 
	@Pattern(regexp="^[A-Za-z0-9 ]+$", message = "Tags must be alphanumeric without special characters") 
	String> tags=new ArrayList<>();
}
