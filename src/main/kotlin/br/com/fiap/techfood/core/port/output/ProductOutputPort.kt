package br.com.fiap.techfood.core.port.output

import br.com.fiap.techfood.core.domain.Product
import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import br.com.fiap.techfood.core.domain.vo.ProductVO
import java.util.*

interface ProductOutputPort {
    fun persist(product: Product): Product
    fun findProductById(id: UUID): Product?
    fun findById(id: UUID): ProductVO?
    fun findByCategory(category: CategoryEnum): List<Product>
    fun findAll(): List<Product>
    fun delete(product: Product)
}