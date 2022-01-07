package com.vivatechrnd.ecommerce.write_modal;

import com.vivatechrnd.ecommerce.api.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Product {
    @AggregateIdentifier
    private String productId;
    private Double price;
    private Integer stock;
    private String description;

    public Product(){}

    @CommandHandler
    public Product(AddProductCommand cmd){
        apply(new AddProductEvent(
                cmd.getProductId(),
                cmd.getPrice(),
                cmd.getStock(),
                cmd.getDescription()
        ));
    }
    @EventSourcingHandler
    public void on(AddProductEvent event){
        productId = event.getProductId();
        price = event.getPrice();
        stock = event.getStock();
        description = event.getDescription();
    }

    @CommandHandler
    public void handle(UpdateProductCommand cmd){
        apply(new UpdateProductEvent(
                cmd.getProductId(),
                cmd.getStock(),
                cmd.getPrice()
        ));
    }

    @EventSourcingHandler
    public void on(UpdateProductEvent event){
        stock = event.getStock();
        price = event.getPrice();
    }

    @CommandHandler
    public void handle(UpdateProductStockCommand cmd){
        apply(new UpdateProductStockEvent(
                cmd.getProductId(),
                cmd.getStock()
        ));
    }

    @EventSourcingHandler
    public void on(UpdateProductStockEvent event){
        productId = event.getProductId();
        stock = event.getStock();
    }
}
