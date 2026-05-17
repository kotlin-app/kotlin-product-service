package com.example.product.model

import jakarta.persistence.*

@Entity
@Table(name = "products")
class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    var name: String,
    var price: Int,
    var description: String,
    var stock: Int,
    var category: String,
)
