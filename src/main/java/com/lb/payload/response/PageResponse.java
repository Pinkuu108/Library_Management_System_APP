package com.lb.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> {

	private List<T> content;
	private int pageNumber;
	private int pageSize;
	private Long totalElements;
	private int totalPages;
	private boolean last;
	private boolean first;
	private boolean empty;

}
