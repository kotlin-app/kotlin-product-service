package com.example.product.controller

import com.example.product.model.Product
import com.example.product.repository.ProductRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var repository: ProductRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
        repository.saveAll(listOf(
            Product(name = "コーヒーメーカー", price = 12000, description = "説明A", stock = 50, category = "家電"),
            Product(name = "ワイヤレスイヤホン", price = 8000, description = "説明B", stock = 100, category = "家電"),
            Product(name = "水筒", price = 3000, description = "説明C", stock = 200, category = "日用品"),
        ))
    }

    @Test
    fun `商品一覧が返る`() {
        mockMvc.get("/products")
            .andExpect {
                status { isOk() }
                jsonPath("$.length()") { value(3) }
                jsonPath("$[0].name") { isNotEmpty() }
                jsonPath("$[0].price") { isNotEmpty() }
            }
    }

    @Test
    fun `存在する商品IDで詳細が返る`() {
        val saved = repository.findAll().first()
        mockMvc.get("/products/${saved.id}")
            .andExpect {
                status { isOk() }
                jsonPath("$.name") { value("コーヒーメーカー") }
                jsonPath("$.price") { value(12000) }
            }
    }

    @Test
    fun `存在しない商品IDで404が返る`() {
        mockMvc.get("/products/999999")
            .andExpect {
                status { isNotFound() }
            }
    }
}
