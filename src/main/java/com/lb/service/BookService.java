package com.lb.service;

import java.util.List;

import com.lb.Exception.BookException;
import com.lb.payload.dto.BookDTO;
import com.lb.payload.request.BookSearchRequest;
import com.lb.payload.response.PageResponse;

public interface BookService {

	BookDTO createBook(BookDTO bookDTO) throws BookException;

	List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException;

	BookDTO getBookById(Long bookId) throws BookException;

	BookDTO getBookByISBN(String isbn) throws BookException;

	BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException;

	void deleteBook(Long bookId) throws BookException;

	void hardDeleteBook(Long bookId) throws BookException;

	PageResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest);

	long getTotalActiveBooks();

	Long getTotalAvailableBooks();

}
