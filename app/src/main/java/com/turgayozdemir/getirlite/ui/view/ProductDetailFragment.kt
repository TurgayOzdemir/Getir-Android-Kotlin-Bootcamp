package com.turgayozdemir.getirlite.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.turgayozdemir.getirlite.R
import com.turgayozdemir.getirlite.data.model.CartItem
import com.turgayozdemir.getirlite.databinding.FragmentListingBinding
import com.turgayozdemir.getirlite.databinding.FragmentProductDetailBinding
import com.turgayozdemir.getirlite.ui.viewmodel.CartViewModel

class ProductDetailFragment : Fragment() {

    private var _binding: FragmentProductDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartViewModel: CartViewModel
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

        (activity as? MainActivity)?.CartButton(true)

        cartViewModel = ViewModelProvider(requireActivity()).get(CartViewModel::class.java)

        (activity as? MainActivity)?.PageTitle("Ürün Detayı")
        (activity as? MainActivity)?.CloseIcon(true)



        arguments?.let{
            val imageURL = ProductDetailFragmentArgs.fromBundle(it).argImageUrl
            val name = ProductDetailFragmentArgs.fromBundle(it).argProductName
            val price = ProductDetailFragmentArgs.fromBundle(it).argProductPrice
            val attribute = ProductDetailFragmentArgs.fromBundle(it).argProductAttribute
            val id = ProductDetailFragmentArgs.fromBundle(it).argProductId

            Picasso.get()
                .load(imageURL)
                .into(binding.detailProductImage)
            binding.productName.text = name
            binding.productPrice.text = "₺${price}"
            binding.attribute.text = attribute?: ""

            updateQuantityDisplay(id!!)
            binding.detailProductItemCount.text = cartViewModel.getQuantityById(id).toString()

            binding.detailProductPlus.setOnClickListener {
                cartViewModel.addItemToCart(CartItem(id!!, imageURL, price.toDouble(), name, attribute, 1))
                updateQuantityDisplay(id!!)
            }
            binding.detailLargeButton.setOnClickListener {
                cartViewModel.addItemToCart(CartItem(id!!, imageURL, price.toDouble(), name, attribute, 1))
                updateQuantityDisplay(id!!)
            }

            binding.detailProductMinus.setOnClickListener {
                cartViewModel.removeItemFromCart(id!!)
                updateQuantityDisplay(id!!)
            }
            binding.detailProductTrashcan.setOnClickListener {
                cartViewModel.removeItemFromCart(id!!)
                updateQuantityDisplay(id!!)
            }



        }



    }

    private fun updateQuantityDisplay(productId: String) {
        val quantity = cartViewModel.getQuantityById(productId)
        if (quantity < 1){
            binding.squareButtonLinearContainer.visibility = View.GONE
            binding.buttonConstraintLayout.visibility = View.VISIBLE
        }
        else if (quantity == 1){
            binding.squareButtonLinearContainer.visibility = View.VISIBLE
            binding.buttonConstraintLayout.visibility = View.GONE
            binding.detailProductMinus.visibility = View.GONE
            binding.detailProductTrashcan.visibility = View.VISIBLE
            binding.detailProductItemCount.text = quantity.toString()
        }
        else{
            binding.squareButtonLinearContainer.visibility = View.VISIBLE
            binding.buttonConstraintLayout.visibility = View.GONE
            binding.detailProductMinus.visibility = View.VISIBLE
            binding.detailProductTrashcan.visibility = View.GONE
            binding.detailProductItemCount.text = quantity.toString()
        }
    }

}