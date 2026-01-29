package com.lb.service.Impl;

import com.lb.Exception.SubscriptionException;
import com.lb.domain.PaymentGateway;
import com.lb.domain.PaymentType;
import com.lb.entity.Subscription;
import com.lb.entity.SubscriptionPlan;
import com.lb.entity.User;
import com.lb.genreRepository.SubscriptionPlanRepository;
import com.lb.genreRepository.SubscriptionRepository;
import com.lb.mapper.SubscriptionMapper;
import com.lb.mapper.SubscriptionPlanMapper;
import com.lb.payload.dto.SubscriptionDTO;
import com.lb.payload.request.PaymentInitiateRequest;
import com.lb.payload.response.PaymentInitiateResponse;
import com.lb.service.PaymentService;
import com.lb.service.SubscriptionService;
import com.lb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPlanMapper subscriptionPlanMapper;
    private final UserService userService;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionMapper subscriptionMapper;
   private final PaymentService paymentService;
    @Override
    public PaymentInitiateResponse subscribe(SubscriptionDTO subscriptionDTO) throws Exception {

        User user = userService.getCurrentUser();
        SubscriptionPlan plan = subscriptionPlanRepository
                .findById(subscriptionDTO.getPlanId()).orElseThrow(
                        () -> new Exception("Plan Not Found")
                );
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDTO,plan,user);
        subscription.initializeFromPlan();
        subscription.setIsActive(false);
        Subscription savedSubscription = subscriptionRepository.save(subscription);

        //create payment (todo)

        PaymentInitiateRequest paymentInitiateRequest = PaymentInitiateRequest
                .builder()
                .userId(user.getId())
                .subscriptionId(subscription.getId())
                .paymentType(PaymentType.MEMBERSHIP)
                .gateway(PaymentGateway.RAZORPAY)
                .amount(subscription.getPrice())
                .description("Library Subscription-"+plan.getName())
                .build();

        return paymentService.initiatePayment(paymentInitiateRequest);
    }

    @Override
    public SubscriptionDTO getUserActiveSubscription(Long userId) throws Exception {
        User user = userService.getCurrentUser();

        Subscription subscription = subscriptionRepository
                .findActiveSubscriptionByUserId(user.getId(), LocalDate.now())
                .orElseThrow(() -> new SubscriptionException("No Active Subscription  Found !"));
        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public SubscriptionDTO cancelSubscription(Long subscriptionId, String reason) throws SubscriptionException {

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionException("Subscription Not Found With Id !" + subscriptionId));

        if (!subscription.getIsActive()) {
            throw new SubscriptionException("Subscription is already inactive !");
        }
        //Mark as cancelled
        subscription.setIsActive(false);
        subscription.setCancelledAt(LocalDateTime.now());
        subscription.setCancelledReason(reason != null ? reason : "Cancelled By User");

        subscription = subscriptionRepository.save(subscription);


        return subscriptionMapper.toDTO(subscription);
    }

    @Override
    public SubscriptionDTO activateSubscription(Long subscriptionId, Long paymentId) throws SubscriptionException {

        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionException("Subscription Not Found With Id !"));

        // verify payment(todo)
        subscription.setIsActive(true);
        subscription = subscriptionRepository.save(subscription);
        return subscriptionMapper.toDTO(subscription);
    }


    @Override
    public List<SubscriptionDTO> getAllSubscriptions(Pageable pageable) {
       List<Subscription> subscriptions = subscriptionRepository.findAll();
        return subscriptionMapper.toDTOList(subscriptions);
    }

    @Override
    public void deactivateExpiredSubscriptions() throws Exception {

        List<Subscription> expiredSubscriptions = subscriptionRepository
                .findExpiredActiveSubscriptions(LocalDate.now());

        for (Subscription subscription : expiredSubscriptions) {
            subscription.setIsActive(false);
            subscriptionRepository.save(subscription);
        }
    }
}
