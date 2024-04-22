package com.turgayozdemir.getirlite.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.turgayozdemir.getirlite.data.model.CartItem
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class CartViewModel : ViewModel() {
    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _totalPrice = MutableLiveData<Double>()
    val totalPrice: LiveData<Double> = _totalPrice

    init {
        _cartItems.value = listOf()

        _cartItems.observeForever { items ->
            _totalPrice.value = items.sumOf { it.price * it.quantity }
        }
    }

    fun addItemToCart(newItem: CartItem) {
        val items = ArrayList(_cartItems.value ?: listOf())
        val existingItem = items.find { it.id == newItem.id }
        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            items.add(newItem)
        }
        _cartItems.value = items
    }

    fun removeItemFromCart(itemId: String) {
        val items = ArrayList(_cartItems.value ?: listOf())
        val item = items.find { it.id == itemId }
        item?.let {
            if (it.quantity > 1) {
                it.quantity -= 1
            } else {
                items.remove(it)
            }
        }
        _cartItems.value = items
    }

    fun clearCart() {
        _cartItems.value = listOf()
    }

    fun getQuantityById(productId: String): Int {
        return cartItems.value?.find { it.id == productId }?.quantity ?: 0
    }

    fun formatNumber(number: Float, isSpecialFormat: Boolean): String {
        val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            decimalSeparator = ','
            groupingSeparator = '.'
        }

        val formatter: DecimalFormat = if (isSpecialFormat) {
            when {
                number >= 1000 -> DecimalFormat("#,##0", symbols)
                number >= 100 -> DecimalFormat("#,##0.0", symbols)
                else -> DecimalFormat("#,##0.00", symbols)
            }
        } else {
            DecimalFormat("#,##0.00", symbols)
        }

        return formatter.format(number)
    }
}