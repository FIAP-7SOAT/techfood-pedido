package br.com.fiap.techfood.app.adapter.output.persistence.repository

import br.com.fiap.techfood.app.adapter.output.persistence.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository : JpaRepository<ProductEntity, UUID> {
    fun findByCategory(category: Int?): List<ProductEntity>
}