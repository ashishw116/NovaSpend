package dev.ashishwagh.novaspend.dto;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

import dev.ashishwagh.novaspend.model.EntryType;
import lombok.Data;

@Data
public class FinanceEntryResponseProjection {

    @Id
    private String id;

    private double amount;
    private String category;
    private LocalDate date;
    private EntryType type;
}
