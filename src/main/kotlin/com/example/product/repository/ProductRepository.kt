package com.example.product.repository

import com.example.product.model.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<Product, Long> {

    @Query("""
        SELECT p FROM Product p WHERE
        (:q IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%'))
                    OR LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%')))
        AND (:category IS NULL OR p.category = :category)
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
    """)
    fun search(
        q: String?,
        category: String?,
        minPrice: Int?,
        maxPrice: Int?,
    ): List<Product>
}
