package br.com.fiap.techfood.app.adapter.input.web.product.dto

import br.com.fiap.techfood.core.domain.enums.CategoryEnum
import java.math.BigDecimal

data class ProductRequest(
    var name: String,
    var description: String,
    var price: BigDecimal,
    var category: CategoryEnum,
    var imageURL: String,
)