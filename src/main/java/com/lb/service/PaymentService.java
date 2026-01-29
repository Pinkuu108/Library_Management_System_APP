package com.lb.service;

import com.lb.payload.dto.PaymentDTO;
import com.lb.payload.request.PaymentInitiateRequest;
import com.lb.payload.request.PaymentVerifyRequest;
import com.lb.payload.response.PaymentInitiateResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest req) throws Exception;

    PaymentDTO verifyPayment(PaymentVerifyRequest req) throws Exception;

    Page<PaymentDTO> getAllPayments(Pageable pageable);

}

