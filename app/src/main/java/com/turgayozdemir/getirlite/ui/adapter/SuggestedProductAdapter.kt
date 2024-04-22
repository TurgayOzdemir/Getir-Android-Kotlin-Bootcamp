package com.turgayozdemir.getirlite.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.turgayozdemir.getirlite.data.model.CartItem
import com.turgayozdemir.getirlite.data.model.SuggestedProduct
import com.turgayozdemir.getirlite.databinding.RecyclerProductBinding
import com.turgayozdemir.getirlite.ui.view.ListingFragmentDirections

class SuggestedProductAdapter(val suggestedProductList : ArrayList<SuggestedProduct>,
                              private val onItemAdded: (CartItem) -> Unit,
                              private val onItemRemoved: (CartItem) -> Unit,
                              var cartItems: Map<String, Int>
                             ) : RecyclerView.Adapter<SuggestedProductAdapter.SuggestedProductHolder>() {

    class SuggestedProductHolder(val binding : RecyclerProductBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestedProductHolder {
        val binding = RecyclerProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SuggestedProductHolder(binding)
    }

    override fun getItemCount(): Int {
        return suggestedProductList.size
    }

    override fun onBindViewHolder(holder: SuggestedProductHolder, position: Int) {


        var url : String = suggestedProductList[position].imageURL ?: suggestedProductList[position].squareThumbnailURL!!
        if (!url.startsWith("https://") && url.startsWith("http://")){
            url = url.replaceFirst("http://", "https://")
        }

        var description : String? = null
        if (suggestedProductList[position].shortDescription != null){
            description = suggestedProductList[position].shortDescription
        }else{
            description = ""
        }

        holder.binding.addCartIcon.setOnClickListener {
            val cartItem = CartItem(
                id = suggestedProductList[position].id,
                imageUrl = url,
                price = suggestedProductList[position].price!!,
                name = suggestedProductList[position].name!!,
                shortDescription = description,
                quantity = 1
            )
            onItemAdded(cartItem)
        }

        holder.binding.removeCartIcon.setOnClickListener {
            val cartItem = CartItem(
                id = suggestedProductList[position].id,
                imageUrl = url,
                price = suggestedProductList[position].price!!,
                name = suggestedProductList[position].name!!,
                shortDescription = description,
                quantity = 1
            )
            onItemRemoved(cartItem)
        }

        holder.binding.minusCartIcon.setOnClickListener {
            val cartItem = CartItem(
                id = suggestedProductList[position].id,
                imageUrl = url,
                price = suggestedProductList[position].price!!,
                name = suggestedProductList[position].name!!,
                shortDescription = description,
                quantity = 1
            )
            onItemRemoved(cartItem)
        }

        val quantity = cartItems[suggestedProductList[position].id] ?: 0

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

        holder.binding.productName.text = suggestedProductList[position].name
        holder.binding.productPrice.text = suggestedProductList[position].priceText

        if (url != null){
            Picasso.get()
                .load(url)
                .into(holder.binding.productImage)
        } else{
            Picasso.get()
                .load(suggestedProductList[position].squareThumbnailURL)
                .into(holder.binding.productImage)
        }


        holder.binding.attribute.text = description

        holder.binding.productImage.setOnClickListener {
            val action = ListingFragmentDirections.listingToDetail(
                url,
                suggestedProductList[position].price!!.toFloat(),
                suggestedProductList[position].name!!,
                suggestedProductList[position].shortDescription
            )
            Navigation.findNavController(it).navigate(action)
        }

    }

    fun updateCartItems(newCartItems: Map<String, Int>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }
}