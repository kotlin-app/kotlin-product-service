package com.example.product.service

import com.example.product.event.OrderCreatedEvent
import com.example.product.repository.ProductRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class StockConsumer(private val repository: ProductRepository) {
    private val log = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["order.created"], groupId = "product-service")
    @Transactional
    fun handleOrderCreated(event: OrderCreatedEvent) {
        val product = repository.findById(event.productId).orElse(null) ?: run {
            log.warn("Product not found: productId=${event.productId}, orderId=${event.orderId}")
            return
        }
        if (product.stock < event.quantity) {
            log.warn("Insufficient stock: productId=${event.productId}, stock=${product.stock}, required=${event.quantity}")
            return
        }
        product.stock -= event.quantity
        repository.save(product)
        log.info("Stock reduced: productId=${event.productId}, remaining=${product.stock}, orderId=${event.orderId}")
    }
}
