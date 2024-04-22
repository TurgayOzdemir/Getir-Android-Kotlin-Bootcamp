package com.turgayozdemir.getirlite.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.turgayozdemir.getirlite.data.model.CartItem
import com.turgayozdemir.getirlite.databinding.RecyclerCartBinding
import com.turgayozdemir.getirlite.databinding.RecyclerProductBinding
import com.turgayozdemir.getirlite.ui.viewmodel.CartViewModel


class CartAdapter(var cartItemList : List<CartItem>,
                  private val onItemAdded: (CartItem) -> Unit,
                  private val onItemRemoved: (CartItem) -> Unit,
                  var cartItems: Map<String, Int>
                 ) : RecyclerView.Adapter<CartAdapter.CartHolder>() {

    class CartHolder(val binding : RecyclerCartBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        val binding = RecyclerCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartHolder(binding)
    }

    override fun onBindViewHolder(holder: CartHolder, position: Int) {

        var url : String = cartItemList[position].imageUrl!!
        if (!url.startsWith("https://") && url.startsWith("http://")){
            url = url.replaceFirst("http://", "https://")
        }

        var description : String? = null

        if (cartItemList[position].shortDescription != null){
            description = cartItemList[position].shortDescription!!
        }
        else if(cartItemList[position].shortDescription != null){
            description = cartItemList[position].shortDescription!!
        }
        else{
            description = ""
        }

        Picasso.get()
            .load(url)
            .into(holder.binding.cartProductImage)

        holder.binding.cartProductName.text = truncateString(cartItemList[position].name)
        holder.binding.cartAttribute.text = description

        var cartViewModel: CartViewModel = CartViewModel()
        holder.binding.cartProductPrice.text = "₺${cartViewModel.formatNumber(cartItemList[position].price.toFloat(),false)}"


        val quantity = cartItems[cartItemList[position].id] ?: 0

        if (quantity > 0){
            if (quantity == 1){
                holder.binding.cartProductMinus.visibility = View.INVISIBLE
                holder.binding.cartProductTrashcan.visibility = View.VISIBLE
            } else{
                holder.binding.cartProductMinus.visibility = View.VISIBLE
                holder.binding.cartProductTrashcan.visibility = View.INVISIBLE
            }
            holder.binding.cartProductItemCount.text = quantity.toString()
        } else{
            println("else")
        }

        holder.binding.cartProductPlus.setOnClickListener {
            val cartItem = CartItem(
                id = cartItemList[position].id,
                imageUrl = url,
                price = cartItemList[position].price!!,
                name = cartItemList[position].name!!,
                shortDescription = description,
                quantity = 1
            )
            onItemAdded(cartItem)
        }

        holder.binding.cartProductMinus.setOnClickListener {
            val cartItem = CartItem(
                id = cartItemList[position].id,
                imageUrl = url,
                price = cartItemList[position].price!!,
                name = cartItemList[position].name!!,
                shortDescription = description,
                quantity = 1
            )
            onItemRemoved(cartItem)
        }
        holder.binding.cartProductTrashcan.setOnClickListener {
            val cartItem = CartItem(
                id = cartItemList[position].id,
                imageUrl = url,
                price = cartItemList[position].price!!,
                name = cartItemList[position].name!!,
                shortDescription = description,
                quantity = 1
            )
            onItemRemoved(cartItem)
        }


    }


    override fun getItemCount(): Int {
        return cartItemList.size
    }

    fun updateItems(newItems: List<CartItem>) {
        cartItemList = newItems
        notifyDataSetChanged()
    }

    fun updateCartMap(newCartItems: Map<String, Int>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }

    fun truncateString(input: String): String {
        return if (input.length > 23) {
            input.substring(0, 21) + "..."
        } else {
            input
        }
    }

}