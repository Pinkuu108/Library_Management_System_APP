package com.lb.service.Impl;

import com.lb.domain.PaymentGateway;
import com.lb.domain.PaymentStatus;
import com.lb.entity.Payment;
import com.lb.entity.Subscription;
import com.lb.entity.User;
import com.lb.event.publisher.PaymentEventPublisher;
import com.lb.genreRepository.PaymentRepository;
import com.lb.genreRepository.SubscriptionRepository;
import com.lb.genreRepository.UserRepository;
import com.lb.mapper.PaymentMapper;
import com.lb.payload.dto.PaymentDTO;
import com.lb.payload.request.PaymentInitiateRequest;
import com.lb.payload.request.PaymentVerifyRequest;
import com.lb.payload.response.PaymentInitiateResponse;
import com.lb.payload.response.PaymentLinkResponse;
import com.lb.service.PaymentService;
import com.lb.service.gateway.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final UserRepository userRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final PaymentMapper paymentMapper;
    private final PaymentEventPublisher paymentEventPublisher;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws Exception {

        User user = userRepository.findById(request.getUserId()).get();
        Payment payment = new Payment();
        payment.setUser(user);
        payment.setPaymentType(request.getPaymentType());
        payment.setGateway(request.getGateway());
        payment.setAmount(request.getAmount());
        payment.setDescription(request.getDescription());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setTransactionId("TXN_" + UUID.randomUUID());
        payment.setInitiatedAt(LocalDateTime.now());

        if (request.getSubscriptionId() != null) {
            Subscription sub = subscriptionRepository.findById(request.getSubscriptionId())
                    .orElseThrow(() -> new Exception("Subscription not found"));
            payment.setSubscription(sub);
        }

        payment = paymentRepository.save(payment);

        PaymentInitiateResponse response = new PaymentInitiateResponse();

        if(request.getGateway()== PaymentGateway.RAZORPAY)
        {
            PaymentLinkResponse paymentLinkResponse=razorpayService.createPaymentLink(
                user,payment

            );
            response=PaymentInitiateResponse.builder()
                    .paymentId(payment.getId())
                    .gateway(payment.getGateway())
                    .checkoutUrl(paymentLinkResponse.getPayment_link_url())
                    .transactionId(paymentLinkResponse.getPayment_link_id())
                    .amount(payment.getAmount())
                    .description(payment.getDescription())
                    .success(true)
                    .message("Payment initiated Successfully")
                    .build();
            payment.setGatewayOrderId(paymentLinkResponse.getPayment_link_id());
        }
        payment.setStatus(PaymentStatus.PROCESSING);
        paymentRepository.save(payment);
//payment initiate event
        return response;
    }

    @Override
    public PaymentDTO verifyPayment(PaymentVerifyRequest req) throws Exception {

        JSONObject paymentDetails =razorpayService.fetchPaymentDetails(
                req.getRazorpayPaymentId()
        );
        JSONObject notes=paymentDetails.getJSONObject("notes");

        Long paymentId=Long.parseLong(notes.optString("payment_Id"));


        Payment payment=paymentRepository.findById(paymentId).get();

        boolean isValid = razorpayService.isValidPayment(req.getRazorpayPaymentId());

        if(PaymentGateway.RAZORPAY==payment.getGateway()){
            if(isValid)
            {
                payment.setGatewayOrderId(req.getRazorpayPaymentId());
            }
        }
        if(isValid){
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setCompletedAt(LocalDateTime.now());
           payment= paymentRepository.save(payment);


            // Publish Payment Success event - todo
            paymentEventPublisher.publishPaymentSuccessEvent(payment);
        }
        return paymentMapper.toDTO(payment);
    }

    @Override
    public Page<PaymentDTO> getAllPayments(Pageable pageable) {
        Page<Payment> payments = paymentRepository.findAll(pageable);

        return payments.map(paymentMapper::toDTO);
    }
}
