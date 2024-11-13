package br.com.fiap.techfood.core.common.exception

open class ProductNotFoundException : RuntimeException {
    companion object {
        const val MESSAGE: String = "Product not found"
    }

    constructor(ex: Exception?) : super(MESSAGE, ex)
    constructor() : super(MESSAGE)
}