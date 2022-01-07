package com.vivatechrnd.ecommerce.read_modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummary {
    @Id
    private String productId;
    private Double price;
    private Integer stock;
    private String description;

}
