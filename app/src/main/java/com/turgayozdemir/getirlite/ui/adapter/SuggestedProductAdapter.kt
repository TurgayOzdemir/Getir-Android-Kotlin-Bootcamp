package com.turgayozdemir.getirlite.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.turgayozdemir.getirlite.data.model.SuggestedProduct
import com.turgayozdemir.getirlite.databinding.RecyclerProductBinding
import com.turgayozdemir.getirlite.ui.view.ListingFragmentDirections

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

        var url : String = suggestedProductList[position].imageURL ?: suggestedProductList[position].squareThumbnailURL!!
        if (!url.startsWith("https://") && url.startsWith("http://")){
            url = url.replaceFirst("http://", "https://")
        }

        if (url != null){
            Picasso.get()
                .load(url)
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

        holder.binding.productImage.setOnClickListener {
            println("Clicked Image")
            val action = ListingFragmentDirections.listingToDetail(
                url,
                suggestedProductList[position].price!!.toFloat(),
                suggestedProductList[position].name!!,
                suggestedProductList[position].shortDescription
            )
            Navigation.findNavController(it).navigate(action)
        }

    }
}