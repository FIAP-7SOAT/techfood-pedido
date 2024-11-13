package br.com.fiap.techfood.app.adapter.output.persistence.entity

import br.com.fiap.techfood.core.common.annotation.NoArgsConstructor
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.*

@Entity
@NoArgsConstructor
@Table(name = "TB_PRODUCTS")
class ProductEntity(
    @Id
    var id: UUID?,

    @Column(nullable = false)
    var name: String? = null,

    @Column(nullable = false)
    var description: String? = null,

    @Column(nullable = false)
    var price: BigDecimal? = null,

    @Column(nullable = false)
    var category: Int? = null,

    @Column(nullable = false)
    var imageURL: String? = null
)