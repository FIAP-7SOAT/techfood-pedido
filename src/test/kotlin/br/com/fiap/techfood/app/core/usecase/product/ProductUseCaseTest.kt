package br.com.fiap.techfood.core.usecase.product

import br.com.fiap.techfood.core.common.exception.ProductNotFoundException
import br.com.fiap.techfood.core.domain.Product
import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import br.com.fiap.techfood.core.domain.vo.ProductVO
import br.com.fiap.techfood.core.port.output.ProductOutputPort
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ProductUseCaseTest {

    private val productOutputPort: ProductOutputPort = Mockito.mock(ProductOutputPort::class.java)
    private val productUseCase = ProductUseCase(productOutputPort)

    @Test
    fun `test create product`() {
        val product = Product(UUID.randomUUID(), "Product1", "Description1", 10.0.toBigDecimal(), CategoryEnum.SNACK.id, "image1.jpg")
        Mockito.`when`(productOutputPort.persist(product)).thenReturn(product)

        val result = productUseCase.create(product)
        assertEquals(product, result)
    }

    @Test
    fun `test get product by id`() {
        val id = UUID.randomUUID()
        val productVO = ProductVO(id, "Product1", "Description1", 10.0.toBigDecimal(), CategoryEnum.SNACK.id, "image1.jpg")
        Mockito.`when`(productOutputPort.findById(id)).thenReturn(productVO)

        val result = productUseCase.getProductById(id)
        assertEquals(productVO, result)
    }

    @Test
    fun `test update product`() {
        val id = UUID.randomUUID()
        val existingProduct = Product(id, "Product1", "Description1", 10.0.toBigDecimal(), CategoryEnum.SNACK.id, "image1.jpg")
        val updatedProduct = Product(id, "Updated Product", "Updated Description", 12.0.toBigDecimal(), CategoryEnum.DRINK.id, "updated.jpg")

        Mockito.`when`(productOutputPort.findProductById(id)).thenReturn(existingProduct)
        Mockito.`when`(productOutputPort.persist(updatedProduct)).thenReturn(updatedProduct)

        val result = productUseCase.update(id, updatedProduct)
        assertEquals(updatedProduct, result)
    }

    @Test
    fun `test delete product`() {
        val id = UUID.randomUUID()
        val product = Product(id, "Product1", "Description1", 10.0.toBigDecimal(), CategoryEnum.SNACK.id, "image1.jpg")

        Mockito.`when`(productOutputPort.findProductById(id)).thenReturn(product)
        Mockito.doNothing().`when`(productOutputPort).delete(product)

        productUseCase.delete(id)

        Mockito.verify(productOutputPort).delete(product)
    }

    @Test
    fun `test product not found exception`() {
        val id = UUID.randomUUID()
        Mockito.`when`(productOutputPort.findProductById(id)).thenReturn(null)

        assertFailsWith<ProductNotFoundException> {
            productUseCase.getProductById(id)
        }
    }

    @Test
    fun `test get product by category`() {
        val category = CategoryEnum.SNACK
        val products = listOf(
            Product(UUID.randomUUID(), "Product1", "Description1", 10.0.toBigDecimal(), category.id, "image1.jpg")
        )

        Mockito.`when`(productOutputPort.findByCategory(category)).thenReturn(products)

        val result = productUseCase.getProductByCategory(category)
        assertEquals(products, result)
    }

    @Test
    fun `test get all products`() {
        val products = listOf(
            Product(UUID.randomUUID(), "Product1", "Description1", 10.0.toBigDecimal(), CategoryEnum.SNACK.id, "image1.jpg"),
            Product(UUID.randomUUID(), "Product2", "Description2", 20.0.toBigDecimal(), CategoryEnum.DRINK.id, "image2.jpg")
        )

        Mockito.`when`(productOutputPort.findAll()).thenReturn(products)

        val result = productUseCase.findAll()
        assertEquals(products, result)
    }


}
