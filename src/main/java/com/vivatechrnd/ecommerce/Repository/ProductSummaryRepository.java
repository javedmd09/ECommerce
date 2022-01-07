package com.vivatechrnd.ecommerce.Repository;

import com.vivatechrnd.ecommerce.read_modal.ProductSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductSummaryRepository extends JpaRepository<ProductSummary, String> {
    List<ProductSummary> findAllByDescriptionContaining(String productName);


}
