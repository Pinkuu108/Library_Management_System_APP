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

import com.lb.Exception.GenreException;
import com.lb.payload.dto.GenreDTO;
import com.lb.payload.response.ApiResponse;
import com.lb.service.GenreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/genres")
public class GenreController {

	private final GenreService genreService;

	@PostMapping("/create")
	public ResponseEntity<GenreDTO> addGenre(@RequestBody GenreDTO genre) {
		GenreDTO createdGenre = genreService.createGenre(genre);
		return ResponseEntity.ok(createdGenre);

	}

	@GetMapping()
	public ResponseEntity<?> getAllGenres() {
		List<GenreDTO> genres = genreService.getAllgenres();
		return ResponseEntity.ok(genres);

	}

	@GetMapping("/{genreId}")
	public ResponseEntity<?> getGenreById(@PathVariable("genreId") Long genreId) throws GenreException {
		GenreDTO genres = genreService.getGenreById(genreId);
		return ResponseEntity.ok(genres);

	}

	@PutMapping("/{genreId}")
	public ResponseEntity<?> updateGenre(@PathVariable("genreId") Long genreId, @RequestBody GenreDTO genre)
			throws GenreException {
		GenreDTO genres = genreService.updateGenre(genreId, genre);
		return ResponseEntity.ok(genres);

	}

	@DeleteMapping("/{genreId}")
	public ResponseEntity<?> deleteGenre(@PathVariable("genreId") Long genreId) throws GenreException {
		genreService.deleteGenre(genreId);
		ApiResponse response = new ApiResponse("Genre deleted -soft delete", true);
		return ResponseEntity.ok(response);

	}

	@DeleteMapping("/{genreId}/hard")
	public ResponseEntity<?> hardDeleteGenre(@PathVariable("genreId") Long genreId) throws GenreException {
		genreService.hardDeleteGenre(genreId);
		ApiResponse response = new ApiResponse("Genre deleted -hard delete", true);
		return ResponseEntity.ok(response);

	}

	@GetMapping("/top-level")
	public ResponseEntity<?> getTopLevelGenres() {
		List<GenreDTO> genres = genreService.getTopLevelGenres();
		return ResponseEntity.ok(genres);

	}

	@GetMapping("/count")
	public ResponseEntity<?> getTotalActiveGenres() {
		Long genres = genreService.getTotalActiveGenres();
		return ResponseEntity.ok(genres);

	}
	@GetMapping("/{id}/book-count")
	public ResponseEntity<?> getBookCountByGenres(@PathVariable Long id) {
		Long count = genreService.getBookCountByGenre(id);
		return ResponseEntity.ok(count);

	}

}
