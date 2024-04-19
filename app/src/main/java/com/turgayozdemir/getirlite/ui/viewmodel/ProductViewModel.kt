package com.turgayozdemir.getirlite.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.turgayozdemir.getirlite.data.model.Product
import com.turgayozdemir.getirlite.data.model.SuggestedProduct
import com.turgayozdemir.getirlite.data.remote.ApiService
import com.turgayozdemir.getirlite.data.remote.RetrofitBuilder
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.turgayozdemir.getirlite.util.Resource

class ProductViewModel : ViewModel() {
    private val apiService = RetrofitBuilder.apiService

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> = _products

    private val _suggestedProducts = MutableLiveData<Resource<List<SuggestedProduct>>>()
    val suggestedProducts: LiveData<Resource<List<SuggestedProduct>>> = _suggestedProducts

    init {
        fetchProducts()
        fetchSuggestedProducts()
    }

    private fun fetchProducts() {
        _products.postValue(Resource.Loading(null))
        viewModelScope.launch {
            try {
                val response = apiService.getProducts()
                if (response.isSuccessful && response.body() != null) {
                    val productsList = response.body()!![0].products
                    _products.postValue(Resource.Success(productsList))
                } else {
                    _products.postValue(Resource.Error("Failed to load products"))
                }
            } catch (e: Exception) {
                _products.postValue(Resource.Error("Network error: ${e.localizedMessage}"))
            }
        }
    }

    private fun fetchSuggestedProducts() {
        _suggestedProducts.postValue(Resource.Loading(null))
        viewModelScope.launch {
            try {
                val response = apiService.getSuggestedProducts()
                if (response.isSuccessful && response.body() != null) {
                    val suggestedProductsList = response.body()!![0].products
                    _suggestedProducts.postValue(Resource.Success(suggestedProductsList))
                } else {
                    _suggestedProducts.postValue(Resource.Error("An error occurred: ${response.errorBody()?.string()}"))
                }
            } catch (e: Exception) {
                _suggestedProducts.postValue(Resource.Error("Check your network connection: ${e.localizedMessage}"))
            }
        }
    }
}