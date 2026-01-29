package com.lb.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.lb.service.BookService;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.lb.Exception.BookException;
import com.lb.entity.Book;
import com.lb.genreRepository.BookRepository;
import com.lb.mapper.BookMapper;
import com.lb.mapper.GenreMapper;
import com.lb.payload.dto.BookDTO;
import com.lb.payload.request.BookSearchRequest;
import com.lb.payload.response.PageResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final GenreMapper genreMapper;

	private final BookRepository bookRepository;

	private final BookMapper bookMapper;

	@Override
	public BookDTO createBook(BookDTO bookDTO) throws BookException {

		if (bookRepository.existsByIsbn(bookDTO.getIsbn())) {
			throw new BookException("book with isbn" + bookDTO.getIsbn() + "already exist");
		}
		Book book = bookMapper.toEntity(bookDTO);

		// total =10
		// availeble=11
		book.isAvailableCopesValid();
		Book savedBook = bookRepository.save(book);

		return bookMapper.toDTO(savedBook);
	}

	@Override
	public List<BookDTO> createBooksBulk(List<BookDTO> bookDTOs) throws BookException {
		List<BookDTO> createdBooks = new ArrayList<BookDTO>();
		for (BookDTO bookDTO : bookDTOs) {
			BookDTO book = createBook(bookDTO);
			createdBooks.add(book);

		}

		return createdBooks;
	}

	@Override
	public BookDTO getBookById(Long bookId) throws BookException {
		Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookException("Book not Found"));
		return bookMapper.toDTO(book);
	}

	@Override
	public BookDTO getBookByISBN(String isbn) throws BookException {
		Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookException("Book not Found"));
		return bookMapper.toDTO(book);

	}

	@Override
	public BookDTO updateBook(Long bookId, BookDTO bookDTO) throws BookException {
		Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new BookException("Book not found "));
		bookMapper.updateEntityFromDTO(bookDTO, existingBook);
		existingBook.isAvailableCopesValid();
		Book savedBook = bookRepository.save(existingBook);
		return bookMapper.toDTO(savedBook);
	}

	@Override
	public void deleteBook(Long bookId) throws BookException {
		Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new BookException("Book not found "));
		existingBook.setActive(false);
		bookRepository.save(existingBook);
	}

	@Override
	public void hardDeleteBook(Long bookId) throws BookException {
		Book existingBook = bookRepository.findById(bookId).orElseThrow(() -> new BookException("Book not found "));
		bookRepository.delete(existingBook);
	}

	@Override
	public PageResponse<BookDTO> searchBooksWithFilters(BookSearchRequest searchRequest) {
		Pageable pageable = createPageable(searchRequest.getPage(), searchRequest.getSize(), searchRequest.getSortBy(),
				searchRequest.getSortDirection());

		Page<Book> bookPage = bookRepository.searchBooksWithFilters(searchRequest.getSearchTerm(),
				searchRequest.getGenreId(), searchRequest.getAvailableOnly(), pageable);
		return convertToPageResponse(bookPage);

	}

	@Override
	public long getTotalActiveBooks() {

		return bookRepository.countByActiveTrue();
	}

	@Override
	public Long getTotalAvailableBooks() {

		return bookRepository.countAvailableBooks();
	}

	private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
		size = Math.min(size, 10);
		size = Math.max(size, 1);

		Sort sort = sortDirection.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		return PageRequest.of(page, size, sort);
	}

	private PageResponse<BookDTO> convertToPageResponse(Page<Book> books) {

		List<BookDTO> bookDTOS = books.getContent().stream().map(bookMapper::toDTO).collect(Collectors.toList());

		return new PageResponse<>(bookDTOS, books.getNumber(), books.getSize(), books.getTotalElements(),
				books.getTotalPages(), books.isLast(), books.isFirst(), books.isEmpty());
	}

}
