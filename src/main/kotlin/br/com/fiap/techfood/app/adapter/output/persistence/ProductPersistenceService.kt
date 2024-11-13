package br.com.fiap.techfood.app.adapter.output.persistence

import br.com.fiap.techfood.app.adapter.output.persistence.mapper.toDomain
import br.com.fiap.techfood.app.adapter.output.persistence.mapper.toEntity
import br.com.fiap.techfood.app.adapter.output.persistence.mapper.toProductVO
import br.com.fiap.techfood.app.adapter.output.persistence.repository.ProductRepository
import br.com.fiap.techfood.core.common.annotation.PersistenceAdapter
import br.com.fiap.techfood.core.domain.Product
import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import br.com.fiap.techfood.core.domain.vo.ProductVO
import br.com.fiap.techfood.core.port.output.ProductOutputPort
import java.util.*

@PersistenceAdapter
class ProductPersistenceService(
    private val productRepository: ProductRepository,
) : ProductOutputPort {
    override fun persist(product: Product): Product {
        val productEntity = product.toEntity()
        val savedEntity = productRepository.save(productEntity)
        return savedEntity.toDomain()
    }

    override fun findProductById(id: UUID): Product? {
        return productRepository.findById(id).orElse(null)?.toDomain()
    }

    override fun findById(id: UUID): ProductVO? {
        return productRepository.findById(id).orElse(null)?.toProductVO()
    }

    override fun findByCategory(category: CategoryEnum): List<Product> {
        return productRepository.findByCategory(category.id).map { it.toDomain() }
    }

    override fun findAll(): List<Product> {
        return productRepository.findAll().map { it.toDomain() }
    }

    override fun delete(product: Product) {
        val productEntity = product.toEntity()
        productRepository.delete(productEntity)
    }


}