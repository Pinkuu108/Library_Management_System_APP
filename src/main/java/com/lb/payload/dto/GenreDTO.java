package com.lb.payload.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenreDTO {  
      // "code": "Mystery","name":"Mystery","description":"Suspence & investigation stories","displayorder":1,"active": true,"parentGenreId":1
	
	
	private Long id;
	@NotBlank(message = "Genre code is Mandatory")
	private String code;

	@NotBlank(message = "Genre name is mndatory")
	private String name;

	@Size(max = 500, message = "Description must not exceed 500 characters")
	private String description;

	@Min(value = 0, message = "Display order can't be negative")
	private Integer displayorder = 0;

	
	private Boolean active;
	
	private Long parentGenreId;
	
	private String parentGenreName;
	
	private List<GenreDTO> subGenre;
	
	
	private Long bookCount;
	
	//@CreationTimestamp
	private LocalDateTime createdAt;

	//@UpdateTimestamp
	private LocalDateTime updatedAt;
	
	
	
	
}
