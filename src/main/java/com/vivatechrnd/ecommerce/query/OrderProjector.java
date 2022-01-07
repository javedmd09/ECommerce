package com.vivatechrnd.ecommerce.query;

import com.vivatechrnd.ecommerce.api.CreateOrderEvent;
import com.vivatechrnd.ecommerce.api.UpdateProductStockCommand;
import com.vivatechrnd.ecommerce.api.UpdateProductStockEvent;
import com.vivatechrnd.ecommerce.read_modal.OrderSummary;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProjector {

    private final OrderSummaryRepository repository;
    private final CommandGateway commandGateway;

    public OrderProjector(OrderSummaryRepository repository, CommandGateway commandGateway) {
        this.repository = repository;
        this.commandGateway = commandGateway;
    }

    @EventHandler
    public void on(CreateOrderEvent event){
        OrderSummary summary = new OrderSummary(
                event.getOrderId(),
                event.getPrice(),
                event.getQty(),
                event.getProductId()
        );
        repository.save(summary);

    }

    @QueryHandler
    public List<OrderSummary> handle(GetOrderQuery query){
        return repository.findAll();
    }
}
