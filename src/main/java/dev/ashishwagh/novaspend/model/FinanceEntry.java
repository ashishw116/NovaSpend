package dev.ashishwagh.novaspend.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Document
@Data
@CompoundIndexes({
	@CompoundIndex(def="{'userId' : 1, 'date': -1}"),
	@CompoundIndex(def="{'userId' : 1, 'type': 1}"),
	@CompoundIndex(def="{'userId' : 1, 'category' : 1}")
})
public class FinanceEntry {
	@Id
	private String id;
	@NotNull
	@DecimalMin(value = "0.01", inclusive = true)
	private double amount;
	@NotNull
	private String category;
	@NotNull
	private LocalDate date;
	@NotNull
	private EntryType type;
	@NotBlank
	private String userId;
	@NotBlank
	private String description;
	private List<String> tags=new ArrayList<>();
}
