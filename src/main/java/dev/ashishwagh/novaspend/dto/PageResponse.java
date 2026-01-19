package dev.ashishwagh.novaspend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {
	private List<T> data;
	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private boolean hasPrevious;
	private boolean hasNext;
}
