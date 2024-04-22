package com.turgayozdemir.getirlite.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.turgayozdemir.getirlite.data.model.CartItem
import com.turgayozdemir.getirlite.databinding.RecyclerProductBinding


class CartAdapter(var cartItemList : List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartHolder>() {

    class CartHolder(val binding : RecyclerProductBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding = RecyclerProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(binding)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {

        println(cartItemList[position].name + " " + cartItemList[position].quantity)
    }


    override fun getItemCount(): Int {
        return cartItemList.size
    }

    fun updateItems(newItems: List<CartItem>) {
        cartItemList = newItems
        notifyDataSetChanged()
    }

}