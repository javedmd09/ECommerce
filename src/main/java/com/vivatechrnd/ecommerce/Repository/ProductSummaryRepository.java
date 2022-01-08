package com.vivatechrnd.ecommerce.Repository;

import com.vivatechrnd.ecommerce.read_modal.ProductSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductSummaryRepository extends JpaRepository<ProductSummary, String> {
    List<ProductSummary> findAllByDescriptionContaining(String productName);

    @Query(value = "Select * from Product_Summary a where a.price > :minPrice and a.price < :maxPrice", nativeQuery = true)
    List<ProductSummary> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);




}
