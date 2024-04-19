package com.turgayozdemir.getirlite.data.model

data class SuggestedProduct(
    val id: String,
    val imageURL: String?,
    val squareThumbnailURL: String?,
    val price: Double?,
    val name: String?,
    val priceText: String?,
    val shortDescription: String?

)