package com.example.product.repository

import com.example.product.model.Product
import org.springframework.data.jpa.repository.JpaRepository

// 商品のCRUD操作を提供するリポジトリ
// JpaRepository を継承することで findAll / findById / save 等が自動生成される
interface ProductRepository : JpaRepository<Product, Long>
