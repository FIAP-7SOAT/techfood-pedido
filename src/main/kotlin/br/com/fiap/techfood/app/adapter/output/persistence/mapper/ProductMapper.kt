package br.com.fiap.techfood.app.adapter.output.persistence.mapper

import br.com.fiap.techfood.app.adapter.output.persistence.entity.ProductEntity
import br.com.fiap.techfood.core.domain.Product
import br.com.fiap.techfood.core.domain.vo.ProductVO

fun Product.toEntity(): ProductEntity =
    ProductEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        category = this.category,
        imageURL = this.imageURL
    )

fun ProductEntity.toDomain(): Product =
    Product(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        category = this.category,
        imageURL = this.imageURL
    )

fun ProductEntity.toProductVO(): ProductVO =
    ProductVO(
        id = this.id,
        name = this.name,
        description = this.description,
        price = this.price,
        category = this.category,
        imageURL = this.imageURL
    )