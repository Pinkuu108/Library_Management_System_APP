package com.lb.genreRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lb.entity.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long>{

	List<Genre> findByActiveTrueOrderByDisplayOrderAsc();

	List<Genre> findByParentGenreIsNullAndActiveTrueOrderByDisplayOrderAsc();

	List<Genre> findByParentGenreIdAndActiveTrueOrderByDisplayOrderAsc(
	        Long parentGenreId
	);

	long countByActiveTrue();

	// @Query("select count(b) from book b where b.genre.id=:genreId")
	// long countBooksByGenre(@Param("genreId") Long genreId);

}
