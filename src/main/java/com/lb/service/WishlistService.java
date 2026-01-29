package com.lb.service;

import com.lb.payload.dto.WishlistDTO;
import com.lb.payload.response.PageResponse;

public interface WishlistService {


    WishlistDTO addToWishlist(Long bookId, String notes) throws Exception;

    void removeFromWishlist(Long bookId) throws Exception;

    PageResponse<WishlistDTO> getMyWishlists(int page, int size) throws Exception;

}
