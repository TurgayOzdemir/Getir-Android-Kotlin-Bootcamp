package com.turgayozdemir.getirlite.data.model

data class ProductContainer(
    val id: String,
    val name: String,
    val productCount: Int,
    val products: List<Product>
)