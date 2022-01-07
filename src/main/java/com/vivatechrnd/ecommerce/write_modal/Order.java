package com.vivatechrnd.ecommerce.write_modal;

import com.vivatechrnd.ecommerce.api.CreateOrderCommand;
import com.vivatechrnd.ecommerce.api.CreateOrderEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class Order {
    @AggregateIdentifier
    private String orderId;
    private Double price;
    private Integer stock;
    private String productId;

    public Order(){}

    @CommandHandler
    public Order(CreateOrderCommand cmd){
        apply(new CreateOrderEvent(
                cmd.getOrderId(),
                cmd.getPrice(),
                cmd.getQty(),
                cmd.getProductId()
        ));
    }
    @EventSourcingHandler
    public void on(CreateOrderEvent event){
        orderId = event.getOrderId();
        price = event.getPrice();
        stock = event.getQty();
        productId = event.getProductId();

    }
}
