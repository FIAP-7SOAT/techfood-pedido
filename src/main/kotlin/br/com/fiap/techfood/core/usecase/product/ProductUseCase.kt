package br.com.fiap.techfood.core.usecase.product

import br.com.fiap.techfood.core.common.annotation.UseCase
import br.com.fiap.techfood.core.common.exception.ProductNotFoundException
import br.com.fiap.techfood.core.domain.Product
import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import br.com.fiap.techfood.core.domain.vo.ProductVO
import br.com.fiap.techfood.core.port.input.ProductInputPort
import br.com.fiap.techfood.core.port.output.ProductOutputPort
import java.util.*

@UseCase
class ProductUseCase(
    private val productOutput: ProductOutputPort
): ProductInputPort {
    override fun create(product: Product): Product {
        return productOutput.persist(product)
    }

    override fun getProductById(id: UUID): ProductVO =
        productOutput.findById(id) ?: throw ProductNotFoundException()

    override fun getProductByCategory(category: CategoryEnum): List<Product> {
        return productOutput.findByCategory(category)}

    override fun findAll(): List<Product> {
        return productOutput.findAll()
    }

    override fun update(id: UUID, product: Product): Product {
        val existingProduct = productOutput.findProductById(id) ?: throw ProductNotFoundException()
        val updatedProduct = existingProduct.copy(
            name = product.name,
            description = product.description,
            price = product.price,
            category = product.category,
            imageURL = product.imageURL
        )
        return productOutput.persist(updatedProduct)
    }

    override fun delete(id: UUID) {
        val existingProduct = productOutput.findProductById(id) ?: throw ProductNotFoundException()
        productOutput.delete(existingProduct)
    }
}
