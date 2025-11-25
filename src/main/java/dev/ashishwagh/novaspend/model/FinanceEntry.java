package dev.ashishwagh.novaspend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document
@Data
public class FinanceEntry {
	@Id
	private String id;
	@NotNull
	private double amount;
	@NotNull
	private String category;
	@NotNull
	private LocalDate date;
	@NotNull
	private EntryType type;
	@NotBlank
	private String description;
	private List<String> tags=new ArrayList<>();
}
