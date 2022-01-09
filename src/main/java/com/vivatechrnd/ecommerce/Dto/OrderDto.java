package com.vivatechrnd.ecommerce.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String orderId;
    private String productId;
    private Double price;
    private Integer stock;
    private Date fromDate;
    private Date toDate;
}
