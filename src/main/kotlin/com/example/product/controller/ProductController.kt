package com.example.product.controller

import com.example.product.model.Product
import com.example.product.repository.ProductRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

data class CreateProductRequest(val name: String, val price: Int, val description: String, val stock: Int, val category: String)
data class UpdateProductRequest(val name: String?, val price: Int?, val description: String?, val stock: Int?, val category: String?)

@Tag(name = "Products", description = "商品情報API")
@RestController
@RequestMapping("/products")
class ProductController(private val repository: ProductRepository) {

    @Operation(summary = "商品一覧・検索取得", description = "q=検索キーワード、category=カテゴリ、minPrice/maxPrice=価格帯でフィルタ可能")
    @ApiResponse(responseCode = "200", description = "取得成功")
    @GetMapping
    fun getAll(
        @RequestParam(required = false) q: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) minPrice: Int?,
        @RequestParam(required = false) maxPrice: Int?,
    ): List<Product> =
        if (q == null && category == null && minPrice == null && maxPrice == null)
            repository.findAll()
        else
            repository.search(q?.takeIf { it.isNotBlank() }, category?.takeIf { it.isNotBlank() }, minPrice, maxPrice)

    @Operation(summary = "商品詳細取得", description = "指定IDの商品を返します")
    @ApiResponse(responseCode = "200", description = "取得成功")
    @ApiResponse(responseCode = "404", description = "商品が存在しない")
    @GetMapping("/{id}")
    fun getById(
        @Parameter(description = "商品ID") @PathVariable id: Long
    ): Product =
        repository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: $id") }

    @Operation(summary = "商品作成（管理者）")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody req: CreateProductRequest): Product =
        repository.save(Product(name = req.name, price = req.price, description = req.description, stock = req.stock, category = req.category))

    @Operation(summary = "商品更新（管理者）")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody req: UpdateProductRequest): Product {
        val p = repository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: $id") }
        req.name?.let { p.name = it }
        req.price?.let { p.price = it }
        req.description?.let { p.description = it }
        req.stock?.let { p.stock = it }
        req.category?.let { p.category = it }
        return repository.save(p)
    }

    @Operation(summary = "在庫更新（管理者）")
    @PatchMapping("/{id}/stock")
    fun updateStock(@PathVariable id: Long, @RequestBody body: Map<String, Int>): Product {
        val p = repository.findById(id).orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: $id") }
        p.stock = body["stock"] ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "stock is required")
        return repository.save(p)
    }

    @Operation(summary = "商品削除（管理者）")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) {
        if (!repository.existsById(id)) throw ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found: $id")
        repository.deleteById(id)
    }
}
