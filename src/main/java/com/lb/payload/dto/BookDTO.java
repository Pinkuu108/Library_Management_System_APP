package com.lb.payload.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

	private Long id;

	@NotBlank(message = "ISBN is mandatory")
	private String isbn;

	@NotBlank(message = "Title is Mandatory")
	@Size(min = 1, max = 255, message = "Title Must be between  1 and 255 Character")
	private String title;

	@NotBlank(message = "Author is Mandatory")
	@Size(min = 1, max = 255, message = "Author Must be between  1 and 255 Character")
	private String author;

	@NotNull(message = "Genre is mandatory")
	private Long genreId;

	private String genreName;

	private String genreCode;

	@Size(max = 100, message = "publisher name must not exceed 100 character")
	private String publisher;

	private LocalDate publicationDate;

	@Size(max = 20, message = "Language must not exceed 20 character")
	private String language;

	@Min(value = 1, message = "Pages must be at least 1 character")
	@Max(value = 50000, message = "Pages must not exceed 50000")
	private Integer pages;

	@Size(max = 2000, message = "Desciption mush not exceed 2000 character")
	private String description;

	@Min(value = 0, message = "Total copies can't be negative")
	@NotNull(message = "Total copies is Mnadatory")
	private Integer totalCopies;

	@Min(value = 0, message = "available")
	@NotNull(message = "Available copies is mandatory")
	private Integer availableCopies;

	@DecimalMin(value = "0.0", inclusive = true, message = "Price can't be negative")
	@Digits(integer = 8, fraction = 2, message = "Price must heve at most 8 digits 2-8")
	private BigDecimal price;

	@Size(max = 500, message = "Image url mush not exceed 500 character")
	private String coverImageUrl;

	private Boolean alreadyHaveLoan;
	
	private Boolean alreadyHaveReservation;
	
	private Boolean active;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;

}
