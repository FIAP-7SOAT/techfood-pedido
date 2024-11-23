package br.com.fiap.techfood.app.adapter.output.persistence

import br.com.fiap.techfood.app.adapter.output.persistence.entity.ProductEntity
import br.com.fiap.techfood.app.adapter.output.persistence.mapper.toDomain
import br.com.fiap.techfood.app.adapter.output.persistence.mapper.toEntity
import br.com.fiap.techfood.app.adapter.output.persistence.repository.ProductRepository
import br.com.fiap.techfood.core.domain.Product
import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

class ProductPersistenceServiceTest {

    private val productRepository: ProductRepository = mockk()
    private val productPersistenceService = ProductPersistenceService(productRepository)

    @Test
    fun `test persist product`() {
        // Arrange
        val product = Product(
            id = UUID.randomUUID(),
            name = "Product1",
            description = "Description1",
            price = 10.0.toBigDecimal(),
            category = CategoryEnum.SNACK.id,
            imageURL = "image1.jpg"
        )

        val productEntity = product.toEntity()

        every { productRepository.save(any()) } returns productEntity

        // Act
        val result = productPersistenceService.persist(product)

        // Assert
        assertEquals(product, result)

        verify {
            productRepository.save(withArg { savedEntity ->
                assertEquals(productEntity.id, savedEntity.id)
                assertEquals(productEntity.name, savedEntity.name)
                assertEquals(productEntity.description, savedEntity.description)
                assertEquals(productEntity.price, savedEntity.price)
                assertEquals(productEntity.category, savedEntity.category)
                assertEquals(productEntity.imageURL, savedEntity.imageURL)
            })
        }
    }

    @Test
    fun `test find product by id`() {
        val id = UUID.randomUUID()
        val productEntity = ProductEntity(
            id, "Product1", "Description1", 10.0.toBigDecimal(), CategoryEnum.SNACK.id, "image1.jpg"
        )

        every { productRepository.findById(id) } returns Optional.of(productEntity)

        val result = productPersistenceService.findProductById(id)
        assertEquals(productEntity.toDomain(), result)
    }

    @Test
    fun `test find product by id not found`() {
        val id = UUID.randomUUID()

        every { productRepository.findById(id) } returns Optional.empty()

        val result = productPersistenceService.findProductById(id)
        assertEquals(null, result)
    }

    @Test
    fun `test find all products`() {
        val products = listOf(
            ProductEntity(UUID.randomUUID(), "Product1", "Description1", 10.0.toBigDecimal(), CategoryEnum.SNACK.id, "image1.jpg"),
            ProductEntity(UUID.randomUUID(), "Product2", "Description2", 20.0.toBigDecimal(), CategoryEnum.DRINK.id, "image2.jpg")
        )

        every { productRepository.findAll() } returns products

        val result = productPersistenceService.findAll()
        assertEquals(products.map { it.toDomain() }, result)
    }


}
