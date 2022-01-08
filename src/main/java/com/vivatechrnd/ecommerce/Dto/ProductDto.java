package com.vivatechrnd.ecommerce.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String productId;
    private Double price;
    private Integer stock;
    private String description;
    private Double priceMin;
    private Double priceMax;
}
