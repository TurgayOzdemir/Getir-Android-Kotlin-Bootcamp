package com.turgayozdemir.getirlite.data.remote

import com.turgayozdemir.getirlite.data.model.Product
import com.turgayozdemir.getirlite.data.model.ProductContainer
import com.turgayozdemir.getirlite.data.model.SuggestedProduct
import com.turgayozdemir.getirlite.data.model.SuggestedProductContainer
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductContainer>>

    @GET("suggestedProducts")
    suspend fun getSuggestedProducts(): Response<List<SuggestedProductContainer>>
}
