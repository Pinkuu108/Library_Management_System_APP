package com.lb.genreRepository;

import com.lb.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
