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
import com.turgayozdemir.getirlite.data.model.Product
import com.turgayozdemir.getirlite.databinding.RecyclerProductBinding
import com.turgayozdemir.getirlite.ui.view.ListingFragmentDirections

class ProductAdapter(val productList : ArrayList<Product>,
                     private val onItemAdded: (CartItem) -> Unit,
                     private val onItemRemoved: (CartItem) -> Unit,
                     var cartItems: Map<String, Int>
                    ) : RecyclerView.Adapter<ProductAdapter.ProductHolder>(){

    class ProductHolder(val binding : RecyclerProductBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding = RecyclerProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {

        holder.binding.productName.text = productList[position].name

        var url : String = productList[position].imageURL!!
        if (!url.startsWith("https://") && url.startsWith("http://")){
            url = url.replaceFirst("http://", "https://")
        }

        var description : String? = null

        if (productList[position].attribute != null){
            description = productList[position].attribute!!
        }
        else if(productList[position].shortDescription != null){
            description = productList[position].shortDescription!!
        }
        else{
            description = ""
        }

        holder.binding.addCartIcon.setOnClickListener {
            val cartItem = CartItem(
                id = productList[position].id,
                imageUrl = url,
                price = productList[position].price!!,
                name = productList[position].name!!,
                shortDescription = description,
                quantity = 1
            )
            onItemAdded(cartItem)
        }

        holder.binding.removeCartIcon.setOnClickListener {
            val cartItem = CartItem(
                id = productList[position].id,
                imageUrl = url,
                price = productList[position].price!!,
                name = productList[position].name!!,
                shortDescription = description,
                quantity = 1
            )
            onItemRemoved(cartItem)
        }

        holder.binding.minusCartIcon.setOnClickListener {
            val cartItem = CartItem(
                id = productList[position].id,
                imageUrl = url,
                price = productList[position].price!!,
                name = productList[position].name!!,
                shortDescription = description,
                quantity = 1
            )
            onItemRemoved(cartItem)
        }

        val quantity = cartItems[productList[position].id] ?: 0
        if (quantity > 0) {
            holder.binding.quantityText.visibility = View.VISIBLE
            holder.binding.quantityText.text = quantity.toString()
            if (quantity == 1){
                holder.binding.removeCartIcon.visibility = View.VISIBLE
                holder.binding.minusCartIcon.visibility = View.GONE
            }
            else{
                holder.binding.removeCartIcon.visibility = View.GONE
                holder.binding.minusCartIcon.visibility = View.VISIBLE
            }
        } else {
            holder.binding.quantityText.visibility = View.INVISIBLE
            holder.binding.removeCartIcon.visibility = View.INVISIBLE
            holder.binding.minusCartIcon.visibility = View.GONE
        }

        Picasso.get()
            .load(url)
            .into(holder.binding.productImage)
        holder.binding.productPrice.text = productList[position].priceText

        holder.binding.attribute.text = description

        holder.binding.productImage.setOnClickListener {
            val action = ListingFragmentDirections.listingToDetail(
                url,
                productList[position].price!!.toFloat(),
                productList[position].name!!,
                productList[position].attribute,
                productList[position].id
            )
            Navigation.findNavController(it).navigate(action)
        }
    }

    fun updateCartItems(newCartItems: Map<String, Int>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }
}