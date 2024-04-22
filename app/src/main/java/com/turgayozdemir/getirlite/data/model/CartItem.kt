package com.turgayozdemir.getirlite.data.model

data class CartItem(
    val id: String,
    val imageUrl: String?,
    val price: Double,
    val name: String,
    val shortDescription: String?,
    var quantity: Int
)