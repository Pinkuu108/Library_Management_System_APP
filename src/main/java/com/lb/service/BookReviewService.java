package com.lb.service;

import com.lb.payload.dto.BookReviewDTO;
import com.lb.payload.request.CreateReviewRequest;
import com.lb.payload.request.UpdateReviewRequest;
import com.lb.payload.response.PageResponse;

public interface BookReviewService {


    BookReviewDTO createReview(CreateReviewRequest request) throws Exception;

    BookReviewDTO updateReview(Long reviewId, UpdateReviewRequest request) throws Exception;

    void deleteReview(Long reviewId) throws Exception;

    PageResponse<BookReviewDTO> getReviewsByBookId(Long id, int page, int size) throws Exception;

}
