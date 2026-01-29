package com.lb.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import com.lb.service.GenreService;
import org.springframework.stereotype.Service;

import com.lb.Exception.GenreException;
import com.lb.entity.Genre;
import com.lb.genreRepository.GenreRepository;
import com.lb.mapper.GenreMapper;
import com.lb.payload.dto.GenreDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

	private final GenreRepository genreRepository;

	private final GenreMapper genreMapper;

	@Override
	public GenreDTO createGenre(GenreDTO genreDTO) {

		Genre genre = genreMapper.toEntity(genreDTO);

		Genre saveGenre = genreRepository.save(genre);

		return genreMapper.toDTO(saveGenre);
	}

	@Override
	public List<GenreDTO> getAllgenres() {

		return genreRepository.findAll().stream().map(genreMapper::toDTO).collect(Collectors.toList());
	}

	@Override
	public GenreDTO getGenreById(Long genreId) throws GenreException {

		Genre genre = genreRepository.findById(genreId).orElseThrow(() -> new GenreException("genre not found"));
		return genreMapper.toDTO(genre);
	}

	@Override
	public GenreDTO updateGenre(Long genreId, GenreDTO genreDTO) throws GenreException {

		Genre existingGenre = genreRepository.findById(genreId)
				.orElseThrow(() -> new GenreException("genre not found"));
		genreMapper.updateEntityFromTo(genreDTO, existingGenre);
		Genre updatedGenre = genreRepository.save(existingGenre);
		return genreMapper.toDTO(updatedGenre);
	}

	@Override
	public void deleteGenre(Long genreId) throws GenreException {

		Genre existingGenre = genreRepository.findById(genreId)
				.orElseThrow(() -> new GenreException("genre not found"));
		existingGenre.setActive(false);
		genreRepository.save(existingGenre);
	}

	@Override
	public void hardDeleteGenre(Long genreId) throws GenreException {
		Genre existingGenre = genreRepository.findById(genreId)
				.orElseThrow(() -> new GenreException("genre not found"));
		genreRepository.delete(existingGenre);

	}

	@Override
	public List<GenreDTO> getAllActiveGenresWithSubGenres() {
		List<Genre> topLevelGenres = genreRepository.findByActiveTrueOrderByDisplayOrderAsc();
		return genreMapper.toDTOList(topLevelGenres);
	}

	@Override
	public List<GenreDTO> getTopLevelGenres() {
		List<Genre> topLevelGenres = genreRepository.findByActiveTrueOrderByDisplayOrderAsc();
		return genreMapper.toDTOList(topLevelGenres);

	}

	@Override
	public long getTotalActiveGenres() {
		
		return genreRepository.countByActiveTrue();
	}

	@Override
	public long getBookCountByGenre(Long genreId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
