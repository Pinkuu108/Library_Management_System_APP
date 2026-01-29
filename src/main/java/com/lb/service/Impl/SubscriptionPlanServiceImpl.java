package com.lb.service.Impl;

import com.lb.entity.SubscriptionPlan;
import com.lb.entity.User;
import com.lb.genreRepository.SubscriptionPlanRepository;
import com.lb.mapper.SubscriptionPlanMapper;
import com.lb.payload.dto.SubscriptionPlanDTO;
import com.lb.service.SubscriptionPlanService;
import com.lb.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository planRepository;
    private final SubscriptionPlanMapper planMapper;
    private final UserService userService;


    @Override
    public SubscriptionPlanDTO createSubscriptionPlan(SubscriptionPlanDTO planDTO) throws Exception {
        if (planRepository.existsByPlanCode(planDTO.getPlanCode())) {
            throw new Exception("Plan code is already Exist");
        }
        SubscriptionPlan plan = planMapper.toEntity(planDTO);
        User currentUser = userService.getCurrentUser();
        plan.setCreatedBy(currentUser.getFullName());
        plan.setUpdatedBy(currentUser.getFullName());
        SubscriptionPlan savedPlan = planRepository.save(plan);
        return planMapper.toDTO(savedPlan);
    }

    @Override
    public SubscriptionPlanDTO updateSubscriptionPlan(Long planId, SubscriptionPlanDTO planDTO) throws Exception {
        SubscriptionPlan existingPlan = planRepository.findById(planId).orElseThrow(
                () -> new Exception("Plan not found")

        );
        planMapper.updateEntity(existingPlan, planDTO);
        User currentUser = userService.getCurrentUser();
        existingPlan.setUpdatedBy(currentUser.getFullName());
        SubscriptionPlan updatedPlan = planRepository.save(existingPlan);
        return planMapper.toDTO(updatedPlan);
    }

    @Override
    public SubscriptionPlanDTO deleteSubscriptionPlan(Long planId) throws Exception {
        SubscriptionPlan existingPlan = planRepository.findById(planId).orElseThrow(
                () -> new Exception("Plan not found")

        );
        planRepository.delete(existingPlan);


        return null;
    }

    @Override
    public List<SubscriptionPlanDTO> getAllSubscriptionPlan() {
        List<SubscriptionPlan> planList = planRepository.findAll();
        return planList.stream().map(planMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public SubscriptionPlan getBySubscriptionPlanCode(String subscriptionPlanCode) throws Exception {
        SubscriptionPlan plan= planRepository.findByPlanCode(subscriptionPlanCode);
        if(plan==null){
            throw new Exception("Plan not found");
        }

        return plan;
    }
}
