package com.turgayozdemir.getirlite.data.model

data class Product(
    val id: String,
    val name: String?,
    val attribute: String?,
    val shortDescription: String?,
    val imageURL: String?,
    val thumbnailURL: String?,
    val price: Double?,
    val priceText: String?
)