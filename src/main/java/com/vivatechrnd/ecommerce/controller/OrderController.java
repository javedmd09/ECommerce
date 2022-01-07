package com.vivatechrnd.ecommerce.controller;

import com.vivatechrnd.ecommerce.api.CreateOrderCommand;
import com.vivatechrnd.ecommerce.api.UpdateProductStockCommand;
import com.vivatechrnd.ecommerce.query.GetOrderQuery;
import com.vivatechrnd.ecommerce.read_modal.OrderSummary;
import com.vivatechrnd.ecommerce.read_modal.ProductSummary;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
public class OrderController {

    private CommandGateway commandGateway;
    private QueryGateway queryGateway;

    public OrderController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/add-order")
    public void handle(@RequestBody ProductSummary productSummary){
        OrderSummary summary = new OrderSummary();
        String orderId = UUID.randomUUID().toString();
        summary.setOrderId(orderId);
        summary.setStock(productSummary.getStock());
        summary.setPrice(productSummary.getPrice());
        summary.setProductId(productSummary.getProductId());
        CreateOrderCommand command = new CreateOrderCommand(
                summary.getOrderId(),
                summary.getPrice(),
                summary.getStock(),
                summary.getProductId()
        );

        commandGateway.send(command);
        UpdateProductStockCommand cmd = new UpdateProductStockCommand(summary.getProductId(), summary.getStock());
        commandGateway.send(cmd);
    }
    @GetMapping("/all-orders")
    public CompletableFuture<List<OrderSummary>> getAllOrder(){
        return queryGateway.query(new GetOrderQuery(),
                ResponseTypes.multipleInstancesOf(OrderSummary.class));
    }
}
