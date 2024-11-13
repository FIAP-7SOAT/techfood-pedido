package br.com.fiap.techfood.core.port.input

import br.com.fiap.techfood.core.domain.Product
import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import br.com.fiap.techfood.core.domain.vo.ProductVO
import java.util.*


interface ProductInputPort {
    fun create(product: Product): Product
    fun getProductById(id: UUID): ProductVO
    fun getProductByCategory(category: CategoryEnum): List<Product>
    fun findAll(): List<Product>
    fun update(id: UUID, product: Product): Product
    fun delete(id: UUID)
}