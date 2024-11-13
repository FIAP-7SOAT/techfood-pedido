package br.com.fiap.techfood.core.domain.vo

import java.math.BigDecimal
import java.util.*

data class ProductVO (
    val id: UUID?,
    var name: String?,
    var description: String?,
    var price: BigDecimal?,
    var category: Int?,
    var imageURL: String?,
)