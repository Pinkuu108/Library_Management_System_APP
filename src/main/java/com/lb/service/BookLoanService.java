package com.lb.service;

import com.lb.domain.BookLoanStatus;
import com.lb.payload.dto.BookLoanDTO;
import com.lb.payload.request.BookLoanSearchRequest;
import com.lb.payload.request.CheckinRequest;
import com.lb.payload.request.CheckoutRequest;
import com.lb.payload.request.RenewalRequest;
import com.lb.payload.response.PageResponse;

public interface BookLoanService {

    BookLoanDTO checkoutBook(CheckoutRequest checkoutRequest) throws Exception;

    BookLoanDTO checkoutBookForUser(Long userId, CheckoutRequest checkoutRequest) throws Exception;

    BookLoanDTO checkinBook(CheckinRequest checkinRequest) throws Exception;

    BookLoanDTO renewCheckout(RenewalRequest renewalRequest) throws Exception;

    PageResponse<BookLoanDTO> getMyBookLoans(BookLoanStatus status
            , int page, int size) throws Exception;

    PageResponse<BookLoanDTO> getBookLoans(BookLoanSearchRequest request) throws Exception;

    int updateOverdueBookLoan();

}
