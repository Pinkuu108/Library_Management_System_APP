package com.lb.domain;

public enum PaymentStatus {


    PENDING,

    /**
     * Payment was successfully processed
     */
    SUCCESS,

    /**
     * Payment failed due to insufficient balance or error
     */
    FAILED,

    /**
     * Payment was cancelled by user
     */
    CANCELLED,
    PROCESSING,
    REFUNDED,



}
