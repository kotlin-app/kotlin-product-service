package com.example.product.controller

import com.example.product.model.Product
import com.example.product.repository.ProductRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@Tag(name = "Products", description = "商品情報API")
@RestController
@RequestMapping("/products")
class ProductController(private val repository: ProductRepository) {

    @Operation(summary = "商品一覧取得", description = "登録されている全商品を返します")
    @ApiResponse(responseCode = "200", description = "取得成功")
    @GetMapping
    fun getAll(): List<Product> = repository.findAll()

    @Operation(summary = "商品詳細取得", description = "指定IDの商品を返します")
    @ApiResponse(responseCode = "200", description = "取得成功")
    @ApiResponse(responseCode = "404", description = "商品が存在しない")
    @GetMapping("/{id}")
    fun getById(
        @Parameter(description = "商品ID") @PathVariable id: Long
    ): Product =
        repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: $id") }
}
