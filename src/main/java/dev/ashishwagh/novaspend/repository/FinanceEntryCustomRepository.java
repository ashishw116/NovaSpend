package dev.ashishwagh.novaspend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;

import dev.ashishwagh.novaspend.dto.FinanceEntryResponseProjection;
import dev.ashishwagh.novaspend.model.EntryType;

public interface FinanceEntryCustomRepository {
	Page<FinanceEntryResponseProjection> filterEntries(
			String userId,
			EntryType type,
			List<String> categories,
			LocalDate fromDate,
			LocalDate toDate,
			int page,
			int size);
}
