package com.vivatechrnd.ecommerce.api

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.util.*

data class CreateOrderEvent(
    val orderId: String,
    val price: Double,
    val qty: Int,
    val productId: String
)

data class AddProductEvent(
    val productId: String,
    val price: Double,
    val stock: Int,
    val description: String
)

data class UpdateProductEvent(
    val productId: String,
    val stock: Int,
    val price: Double
)

data class UpdateProductStockEvent(
    val productId: String,
    val stock: Int
)