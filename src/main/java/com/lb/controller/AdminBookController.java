package com.lb.controller;

import com.lb.Exception.BookException;
import com.lb.payload.dto.BookDTO;
import com.lb.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/books")
public class AdminBookController {
    private final BookService bookService;
    /*
     * Create Book
     * POST /api/books
     */
    @PostMapping()
    public ResponseEntity<BookDTO> createBook(@Valid @RequestBody BookDTO bookDTO) throws BookException {
        BookDTO createdBook = bookService.createBook(bookDTO);
        return ResponseEntity.ok(createdBook);
    }
}
