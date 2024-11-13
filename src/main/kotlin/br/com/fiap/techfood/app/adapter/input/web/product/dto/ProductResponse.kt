package br.com.fiap.techfood.app.adapter.input.web.product.dto

import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import java.math.BigDecimal
import java.util.*

data class ProductResponse (
    var id: UUID?,
    var name: String?,
    var description: String?,
    var price: BigDecimal?,
    var category: CategoryEnum?,
    var imageURL: String?,
)