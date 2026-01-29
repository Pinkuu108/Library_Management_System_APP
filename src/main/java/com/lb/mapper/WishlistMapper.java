package com.lb.mapper;

import com.lb.entity.Wishlist;
import com.lb.payload.dto.WishlistDTO;
import org.springframework.stereotype.Component;

@Component
public class WishlistMapper {


    private final BookMapper bookMapper;

    public WishlistMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public WishlistDTO toDTO(Wishlist wishlist) {
        if (wishlist == null) {
            return null;
        }

        WishlistDTO dto = new WishlistDTO();
        dto.setId(wishlist.getId());

        // User information
        if (wishlist.getUser() != null) {
            dto.setUserId(wishlist.getUser().getId());
            dto.setUserFullName(wishlist.getUser().getFullName());
        }

        // Book information
        if (wishlist.getBook() != null) {
            dto.setBook(bookMapper.toDTO(wishlist.getBook()));
        }

        // Other fields
        dto.setAddedAt(wishlist.getAddedAt());
        dto.setNotes(wishlist.getNotes());

        return dto;
    }
}
