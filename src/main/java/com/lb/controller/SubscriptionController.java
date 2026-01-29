package com.lb.controller;

import com.lb.Exception.SubscriptionException;
import com.lb.payload.dto.SubscriptionDTO;
import com.lb.payload.response.ApiResponse;
import com.lb.payload.response.PaymentInitiateResponse;
import com.lb.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(
            @RequestBody SubscriptionDTO subscription) throws Exception {
        PaymentInitiateResponse dto = subscriptionService.subscribe(subscription);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/user/active")
    public ResponseEntity<?> getUserActiveAllSubscriptions(
            @RequestParam(required = false) Long userId
    ) throws Exception {

        SubscriptionDTO dto = subscriptionService
                .getUserActiveSubscription(userId);
        return ResponseEntity.ok(dto);

    }

    @GetMapping("/admin")
    public ResponseEntity<?> getAllSubscriptions() {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        List<SubscriptionDTO> dtoList = subscriptionService.getAllSubscriptions(pageable);
        return ResponseEntity.ok(dtoList);

    }


    @GetMapping("/admin/deactivate-expired")
    public ResponseEntity<?> deactivateExpiredSubscriptions() throws Exception {
        int page = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(page, size);
        subscriptionService.deactivateExpiredSubscriptions();
        ApiResponse res = new ApiResponse("Task Done!", true);
        return ResponseEntity.ok(res);

    }

    @PostMapping("/cancel/{subscriptionId}")
    public ResponseEntity<?> cancelSubscription(
            @PathVariable Long subscriptionId,
            @RequestParam(required = false) String reason
    ) throws SubscriptionException {

        SubscriptionDTO subscription = subscriptionService
                .cancelSubscription(subscriptionId, reason);

        return ResponseEntity.ok(subscription);
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateSubscription(
            @RequestParam Long subscriptionId,
            @RequestParam Long paymentId
    ) throws SubscriptionException {

        SubscriptionDTO subscription = subscriptionService
                .activateSubscription(subscriptionId, paymentId);

        return ResponseEntity.ok(subscription);
    }


}
