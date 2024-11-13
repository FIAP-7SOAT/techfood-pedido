package br.com.fiap.techfood.app.adapter.input.web.product

import br.com.fiap.techfood.app.adapter.input.web.product.dto.ProductRequest
import br.com.fiap.techfood.app.adapter.input.web.product.dto.ProductResponse
import br.com.fiap.techfood.app.adapter.input.web.product.mapper.toDomain
import br.com.fiap.techfood.app.adapter.input.web.product.mapper.toProductResponse
import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import br.com.fiap.techfood.core.port.input.ProductInputPort
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/products")
class ProductResource(
    private val productInput: ProductInputPort
) {

    @PostMapping
    fun create(@RequestBody request: ProductRequest): ProductResponse {
        val product = productInput.create(request.toDomain())
        return product.toProductResponse()
    }

    @GetMapping("/{id}")
    fun getProductById(@PathVariable id: UUID): ProductResponse {
        val product = productInput.getProductById(id)
        return product.toProductResponse()
    }

    @GetMapping("/category")
    fun searchProductByCategory(@RequestParam name: CategoryEnum): List<ProductResponse> {
        val products = productInput.getProductByCategory(name)
        return products.map { it.toProductResponse() }
    }

    @GetMapping
    fun findAll(): List<ProductResponse> {
        val products = productInput.findAll()
        return products.map { it.toProductResponse() }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID) {
        productInput.delete(id)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: ProductRequest): ProductResponse {
        val updatedProduct = productInput.update(id, request.toDomain())
        return updatedProduct.toProductResponse()
    }

}