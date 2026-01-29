package com.lb.service;

import java.awt.print.Pageable;
import java.util.List;

import org.hibernate.query.Page;

import com.lb.Exception.GenreException;
import com.lb.payload.dto.GenreDTO;

public interface GenreService {

	GenreDTO createGenre(GenreDTO genre);

	List<GenreDTO> getAllgenres();

	GenreDTO getGenreById(Long genreId) throws GenreException;

	GenreDTO updateGenre(Long genreId, GenreDTO genre) throws GenreException;

	void deleteGenre(Long genreId) throws GenreException;

	void hardDeleteGenre(Long genreId) throws GenreException;

	List<GenreDTO> getAllActiveGenresWithSubGenres();

	List<GenreDTO> getTopLevelGenres();

	//Page<GenreDTO> searchGenres(String searchTerm, Pageable pageable);

	long getTotalActiveGenres();

	long getBookCountByGenre(Long genreId);
}
