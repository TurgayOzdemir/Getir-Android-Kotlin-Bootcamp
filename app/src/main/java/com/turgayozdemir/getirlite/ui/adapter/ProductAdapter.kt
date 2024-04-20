package com.turgayozdemir.getirlite.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.turgayozdemir.getirlite.data.model.Product
import com.turgayozdemir.getirlite.databinding.RecyclerProductBinding

class ProductAdapter(val productList : ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

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

        Picasso.get()
            .load(url)
            .into(holder.binding.productImage)
        holder.binding.productPrice.text = productList[position].priceText

        if (productList[position].attribute != null){
            holder.binding.attribute.text = productList[position].attribute
        }
        else if(productList[position].shortDescription != null){
            holder.binding.attribute.text = productList[position].shortDescription
        }
        else{
            holder.binding.attribute.text = ""
        }
    }
}