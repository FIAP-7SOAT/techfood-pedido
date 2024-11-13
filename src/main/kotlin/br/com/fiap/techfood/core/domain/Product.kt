package br.com.fiap.techfood.core.domain

import java.math.BigDecimal
import java.util.UUID

data class Product(
    val id: UUID?,
    var name: String?,
    var description: String?,
    var price: BigDecimal?,
    var category: Int?,
    var imageURL: String?,
)