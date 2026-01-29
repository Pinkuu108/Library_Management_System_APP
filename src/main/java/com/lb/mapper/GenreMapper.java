package com.lb.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lb.LibraryManagementSystemAppApplication;
import com.lb.controller.GenreController;
import com.lb.entity.Genre;
import com.lb.genreRepository.GenreRepository;
import com.lb.payload.dto.GenreDTO;

import lombok.RequiredArgsConstructor;

@Component

public class GenreMapper {

	//@Autowired
	//private  GenreController genreController;
	//@Autowired
	//private  LibraryManagementSystemAppApplication libraryManagementSystemAppApplication;

	@Autowired
	private GenreRepository genreRepository;

	
	
	/*
	 * GenreMapper(LibraryManagementSystemAppApplication
	 * libraryManagementSystemAppApplication, GenreController genreController) {
	 * this.libraryManagementSystemAppApplication =
	 * libraryManagementSystemAppApplication; this.genreController =
	 * genreController; }
	 */
	public GenreDTO toDTO(Genre saveGenre) {
		if (saveGenre == null) {
			return null;
		}
		GenreDTO dto = GenreDTO.builder().id(saveGenre.getId()).code(saveGenre.getCode()).name(saveGenre.getName())
				.description(saveGenre.getDescription()).displayorder(saveGenre.getDisplayOrder())
				.active(saveGenre.getActive()).createdAt(saveGenre.getCreatedAt()).updatedAt(saveGenre.getUpdatedAt())
				.build();

		if (saveGenre.getParentGenre() != null) {
			dto.setParentGenreId(saveGenre.getParentGenre().getId());
			dto.setParentGenreName(saveGenre.getParentGenre().getName());
		}
		if (saveGenre.getSubGenre() != null && !saveGenre.getSubGenre().isEmpty()) {
			dto.setSubGenre(saveGenre.getSubGenre().stream().filter(subGenre -> subGenre.getActive())
					.map(subGenre -> toDTO(subGenre)).collect(Collectors.toList()));
		}

		// dto.getBookCount(Long)
		return dto;
	}

	public Genre toEntity(GenreDTO genreDTO) {
		if (genreDTO == null) {
			return null;
		}

		Genre genre = Genre.builder().code(genreDTO.getCode()).name(genreDTO.getName())
				.description(genreDTO.getDescription()).displayOrder(genreDTO.getDisplayorder()).active(true).build();

		if (genreDTO.getParentGenreId() != null) {
			genreRepository.findById(genreDTO.getParentGenreId()).ifPresent(genre::setParentGenre);
			;
			// genre.setParentGenre(parGenre);
		}
		return genre;
	}

	public void updateEntityFromTo(GenreDTO dto, Genre existingGenre) {
		if (dto == null || existingGenre == null) {
			return;
		}
		existingGenre.setCode(dto.getCode());
		existingGenre.setName(dto.getName());
		existingGenre.setDescription(dto.getDescription());
		existingGenre.setDisplayOrder(dto.getDisplayorder() != null ? dto.getDisplayorder() : 0);
		if (dto.getActive() != null) {
			existingGenre.setActive(dto.getActive());
		}
		if (dto.getParentGenreId() != null) {
			genreRepository.findById(dto.getParentGenreId()).ifPresent(existingGenre::setParentGenre);
		}

	}

	public List<GenreDTO> toDTOList(List<Genre> genreList) {
		return genreList.stream().map(genre -> toDTO(genre)).collect(Collectors.toList());
	}
}
