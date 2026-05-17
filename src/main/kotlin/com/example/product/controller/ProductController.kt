package com.example.product.controller

import com.example.product.model.Product
import com.example.product.repository.ProductRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

// 商品サービスのRESTコントローラー
// BFFからのリクエストを受け取り、DBから商品データを返す
@RestController
@RequestMapping("/products")
class ProductController(private val repository: ProductRepository) {

    // GET /products → 全件返す
    @GetMapping
    fun getAll(): List<Product> = repository.findAll()

    // GET /products/{id} → 該当商品を返す（存在しない場合は404）
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Product =
        repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: $id") }
}
