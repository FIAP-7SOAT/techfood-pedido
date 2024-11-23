package br.com.fiap.techfood.app.adapter.input.web.product

import br.com.fiap.techfood.app.adapter.input.web.product.dto.ProductRequest
import br.com.fiap.techfood.core.common.exception.ProductNotFoundException
import br.com.fiap.techfood.core.domain.Product
import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import br.com.fiap.techfood.core.domain.vo.ProductVO
import br.com.fiap.techfood.core.port.input.ProductInputPort
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class ProductResourceTest {

    private val productInputPort = mockk<ProductInputPort>()
    private val productResource = ProductResource(productInputPort)
    private val mockMvc: MockMvc = MockMvcBuilders.standaloneSetup(productResource).build()

    @Test
    fun `test create product`() {
        val productRequest = ProductRequest(
            "Product1",
            "Description1",
            10.0.toBigDecimal(),
            CategoryEnum.SNACK,
            "image1.jpg"
        )
        val product = Product(
            UUID.randomUUID(),
            productRequest.name,
            productRequest.description,
            productRequest.price,
            productRequest.category.id,
            productRequest.imageURL
        )

        every { productInputPort.create(any()) } returns product

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{"name":"Product1","description":"Description1","price":10.0,"category":"SNACK","imageURL":"image1.jpg"}"""
                )
        ).andExpect(status().isCreated())
            .andExpect {
                content().json(
                    """{
                   "id": "${product.id}",
                   "name": "${product.name}",
                   "description": "${product.description}",
                   "price": ${product.price},
                   "category": ${product.category},
                   "imageURL": "${product.imageURL}"
                }"""
                )
            }

        verify { productInputPort.create(any()) }
    }


    @Test
    fun `test get product by id`() {
        val id = UUID.randomUUID()
        val productVO = ProductVO(
            id,
            "Product1",
            "Description1",
            10.0.toBigDecimal(),
            CategoryEnum.SNACK.id,
            "image1.jpg"
        )

        every { productInputPort.getProductById(id) } returns productVO

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products/$id"))
            .andExpect(status().isOk())
            .andExpect {
                content().json(
                    """{
                   "id": "$id",
                   "name": "${productVO.name}",
                   "description": "${productVO.description}",
                   "price": ${productVO.price},
                   "category": ${productVO.category},
                   "imageURL": "${productVO.imageURL}"
                }"""
                )
            }

        verify { productInputPort.getProductById(id) }
    }



    @Test
    fun `test search products by category`() {
        val products = listOf(
            Product(
                UUID.randomUUID(),
                "Product1",
                "Description1",
                10.0.toBigDecimal(),
                CategoryEnum.SNACK.id,
                "image1.jpg"
            )
        )

        every { productInputPort.getProductByCategory(CategoryEnum.SNACK) } returns products

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/products/category")
                .param("name", "SNACK")
        ).andExpect(status().isOk())
            .andExpect {
                content().json(
                    """[{
                   "id": "${products[0].id}",
                   "name": "${products[0].name}",
                   "description": "${products[0].description}",
                   "price": ${products[0].price},
                   "category": ${products[0].category},
                   "imageURL": "${products[0].imageURL}"
                }]"""
                )
            }

        verify { productInputPort.getProductByCategory(CategoryEnum.SNACK) }
    }



    @Test
    fun `test delete product`() {
        val id = UUID.randomUUID()

        every { productInputPort.delete(id) } returns Unit

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/products/$id"))
            .andExpect(status().isNoContent())

        verify { productInputPort.delete(id) }
    }

    @Test
    fun `test get all products`() {
        val products = listOf(
            Product(
                UUID.randomUUID(),
                "Product1",
                "Description1",
                10.0.toBigDecimal(),
                CategoryEnum.SNACK.id,
                "image1.jpg"
            )
        )

        every { productInputPort.findAll() } returns products

        mockMvc.perform(MockMvcRequestBuilders.get("/api/products"))
            .andExpect(status().isOk())
            .andExpect {
                content().json(
                    """[{
                   "id": "${products[0].id}",
                   "name": "${products[0].name}",
                   "description": "${products[0].description}",
                   "price": ${products[0].price},
                   "category": ${products[0].category},
                   "imageURL": "${products[0].imageURL}"
                }]"""
                )
            }

        verify { productInputPort.findAll() }
    }

    @Test
    fun `test update product`() {
        val id = UUID.randomUUID()
        val productRequest = ProductRequest(
            "Updated Product",
            "Updated Description",
            12.0.toBigDecimal(),
            CategoryEnum.DRINK,
            "updated.jpg"
        )
        val updatedProduct = Product(
            id,
            productRequest.name,
            productRequest.description,
            productRequest.price,
            productRequest.category.id,
            productRequest.imageURL
        )

        every { productInputPort.update(id, any()) } returns updatedProduct

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/products/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    """{
                   "name": "Updated Product",
                   "description": "Updated Description",
                   "price": 12.0,
                   "category": "DRINK",
                   "imageURL": "updated.jpg"
                }"""
                )
        ).andExpect(status().isOk())
            .andExpect {
                content().json(
                    """{
                   "id": "$id",
                   "name": "${updatedProduct.name}",
                   "description": "${updatedProduct.description}",
                   "price": ${updatedProduct.price},
                   "category": ${updatedProduct.category},
                   "imageURL": "${updatedProduct.imageURL}"
                }"""
                )
            }

        verify { productInputPort.update(id, any()) }
    }

    @Test
    fun `should return 404 when product not found`() {
        val productId = UUID.randomUUID()

        // Mocking getProductById to throw ProductNotFoundException
        every { productInputPort.getProductById(productId) } throws ProductNotFoundException()

        val exception = kotlin.runCatching {
            productResource.getProductById(productId)
        }

        assert(exception.isFailure)
        assert(exception.exceptionOrNull() is ProductNotFoundException)
    }


}
