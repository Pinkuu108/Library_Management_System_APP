package com.lb.payload.response;

import com.lb.domain.PaymentGateway;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentInitiateResponse {



    private Long paymentId;

    private PaymentGateway gateway;

    private String transactionId;


    private String razorpayOrderId;

    private Long amount;


    private String description;

    // Frontend should redirect user to this URL for Payment
    private String checkoutUrl;

    private String message;

    private Boolean success;

}
