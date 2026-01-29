package com.lb.service;

import com.lb.Exception.SubscriptionException;
import com.lb.payload.dto.SubscriptionDTO;
import com.lb.payload.response.PaymentInitiateResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SubscriptionService {

    PaymentInitiateResponse subscribe(SubscriptionDTO subscriptionDTO) throws Exception;

    //PaymentInitiateResponse
    SubscriptionDTO getUserActiveSubscription(Long userId) throws Exception;

    SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException;

    SubscriptionDTO activateSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException;

    List<SubscriptionDTO> getAllSubscriptions(Pageable pageable);


    void deactivateExpiredSubscriptions() throws Exception;


}
