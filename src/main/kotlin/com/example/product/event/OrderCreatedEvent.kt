package com.example.product.event

data class OrderCreatedEvent(
    val orderId: Long,
    val productId: Long,
    val quantity: Int
)
