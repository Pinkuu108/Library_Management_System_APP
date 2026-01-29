package com.lb.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lb.Exception.BookException;
import com.lb.payload.dto.BookDTO;
import com.lb.payload.request.BookSearchRequest;
import com.lb.payload.response.ApiResponse;
import com.lb.payload.response.PageResponse;
import com.lb.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

	private final BookService bookService;



	/*
	 * Create Books in Bulk 
	 * POST /api/books/bulk
	 */
	@PostMapping("/bulk")
	public ResponseEntity<?> createBooksBulk(@Valid @RequestBody List<BookDTO> bookDTOS) throws BookException {
		List<BookDTO> createdBook = bookService.createBooksBulk(bookDTOS);
		return ResponseEntity.ok(createdBook);
	}
	/*
	 * Get Book By Id GET /api/books/{id}
	 */

	@GetMapping("/{id}")
	public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) throws BookException {
		BookDTO book = bookService.getBookById(id);
		return ResponseEntity.ok(book);
	}

	/*
	 * Update Book By Id
	 *  PUT /api/books/{id}
	 */
	@PutMapping("/{id}")
	public ResponseEntity<BookDTO> updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO)
			throws BookException {
		BookDTO updateBook = bookService.updateBook(id, bookDTO);

		return ResponseEntity.ok(updateBook);
	}

	/*
	 * Soft Delete Book By Id
	 *  DELETE /api/books/{id}
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteBook(@PathVariable Long id) throws BookException {
		bookService.deleteBook(id);
		return ResponseEntity.ok(new ApiResponse("Book Deleted sucessfully", true));
	}

	/*
	 * Hard Delete Book By Id (Permanent Delete)
	 *  DELETE /api/books/{id}/permanent
	 */
	@DeleteMapping("/{id}/permanent")
	public ResponseEntity<ApiResponse> hardDeleteBook(@PathVariable Long id) throws BookException {
		bookService.hardDeleteBook(id);
		return ResponseEntity.ok(new ApiResponse("Book Permanently Deleted sucessfully", true));
	}
	
	@GetMapping
	public ResponseEntity<PageResponse<BookDTO>> searchBooks(
	        @RequestParam(required = false) Long genreId,
	        @RequestParam(required = false, defaultValue = "false") Boolean availableOnly,
	        @RequestParam(defaultValue = "true") boolean activeOnly,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "20") int size,
	        @RequestParam(defaultValue = "createdAt") String sortBy,
	        @RequestParam(defaultValue = "DESC") String sortDirection) {

	    // Build search request from query parameters
	    BookSearchRequest searchRequest = new BookSearchRequest();
	    searchRequest.setGenreId(genreId);
	    searchRequest.setAvailableOnly(availableOnly);
	    searchRequest.setAvailableOnly(activeOnly);
	    searchRequest.setPage(page);
	    searchRequest.setSize(size);
	    searchRequest.setSortBy(sortBy);
	    searchRequest.setSortDirection(sortDirection);

	    PageResponse<BookDTO> books = bookService.searchBooksWithFilters(searchRequest);
	    return ResponseEntity.ok(books);
	}

	//Search Book
	// POST /api/books/search
	@PostMapping("/search")
	public ResponseEntity<PageResponse<BookDTO>> advanceSearch(@RequestBody BookSearchRequest searchRequest)
	{
		PageResponse<BookDTO> books=bookService.searchBooksWithFilters(searchRequest);
		return ResponseEntity.ok(books);
	}
	
	//book stats
	// GET /api/books/stats
	@GetMapping("/stats")
	public ResponseEntity<BookStatsResponse> getBookStats()
	{
		long totalActive=bookService.getTotalActiveBooks();
		long totalAvailable=bookService.getTotalAvailableBooks();
		BookStatsResponse stats=new BookStatsResponse(totalActive,totalAvailable);
		return ResponseEntity.ok(stats);
	}
	
	//statistics reponse DTO
	public static class BookStatsResponse 
	{
		public long totalActiveBooks;
		public long totalAvailableBooks;
		public BookStatsResponse(long totalActiveBooks,long totalAvailableBooks )
		{
			this.totalActiveBooks=totalActiveBooks;
			this.totalAvailableBooks=totalAvailableBooks;
		}
	}
	
	
	
	
	
	
	
	
	
	
}
