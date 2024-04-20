package com.turgayozdemir.getirlite.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.turgayozdemir.getirlite.R
import com.turgayozdemir.getirlite.databinding.FragmentListingBinding
import com.turgayozdemir.getirlite.databinding.FragmentProductDetailBinding

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.PageTitle("Ürün Detayı")
        (activity as? MainActivity)?.CloseIcon(true)

        arguments?.let{
            val imageURL = ProductDetailFragmentArgs.fromBundle(it).argImageUrl
            val name = ProductDetailFragmentArgs.fromBundle(it).argProductName
            val price = ProductDetailFragmentArgs.fromBundle(it).argProductPrice
            val attribute = ProductDetailFragmentArgs.fromBundle(it).argProductAttribute

            Picasso.get()
                .load(imageURL)
                .into(binding.detailProductImage)
            binding.productName.text = name
            binding.productPrice.text = "₺${price}"
            binding.attribute.text = attribute?: ""

        }

    }

}