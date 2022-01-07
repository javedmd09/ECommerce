package com.vivatechrnd.ecommerce.read_modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSummary {
    @Id
    private String orderId;
    private Double price;
    private Integer stock;
    private String productId;


    public OrderSummary(String orderID, Double price, Integer stock, String productId) {
        this.orderId = orderID;
        this.price = price;
        this.stock = stock;
        this.productId = productId;
    }
    @ManyToOne
    @JoinColumn(name = "productId", insertable = false, updatable = false)
    private ProductSummary summary;
}
