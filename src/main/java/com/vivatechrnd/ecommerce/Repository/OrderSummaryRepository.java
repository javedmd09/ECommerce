package com.vivatechrnd.ecommerce.Repository;

import com.vivatechrnd.ecommerce.read_modal.OrderSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderSummaryRepository extends JpaRepository<OrderSummary, UUID> {
}
