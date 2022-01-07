package com.vivatechrnd.ecommerce.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateOrderCommand(
    @TargetAggregateIdentifier
    val orderId: String,
    val price: Double,
    val qty: Int,
    val productId: String
)

data class AddProductCommand(
    @TargetAggregateIdentifier
    val productId: String,
    val price: Double,
    val stock: Int,
    val description: String
)

data class UpdateProductCommand(
    @TargetAggregateIdentifier
    val productId: String,
    val stock: Int,
    val price: Double
)

data class UpdateProductStockCommand(
    @TargetAggregateIdentifier
    val productId: String,
    val stock: Int
)