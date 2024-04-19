package com.turgayozdemir.getirlite.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.turgayozdemir.getirlite.data.model.SuggestedProduct
import com.turgayozdemir.getirlite.databinding.RecyclerProductBinding
class SuggestedProductAdapter(val suggestedProductList : ArrayList<SuggestedProduct>) : RecyclerView.Adapter<SuggestedProductAdapter.SuggestedProductHolder>() {

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
        holder.binding.productName.text = suggestedProductList[position].name
        holder.binding.productPrice.text = suggestedProductList[position].priceText
        if (suggestedProductList[position].imageURL != null){
            Picasso.get()
                .load(suggestedProductList[position].imageURL)
                .into(holder.binding.productImage)
        }
        else{
            Picasso.get()
                .load(suggestedProductList[position].squareThumbnailURL)
                .into(holder.binding.productImage)
        }


        if (suggestedProductList[position].shortDescription != null){
            holder.binding.attribute.text = suggestedProductList[position].shortDescription
        }
        else{
            holder.binding.attribute.text = ""
        }

    }
}