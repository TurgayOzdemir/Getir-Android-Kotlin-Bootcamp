package com.turgayozdemir.getirlite.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.turgayozdemir.getirlite.R
import com.turgayozdemir.getirlite.data.model.CartItem
import com.turgayozdemir.getirlite.databinding.FragmentBasketBinding
import com.turgayozdemir.getirlite.databinding.FragmentListingBinding
import com.turgayozdemir.getirlite.databinding.FragmentProductDetailBinding
import com.turgayozdemir.getirlite.ui.adapter.CartAdapter
import com.turgayozdemir.getirlite.ui.adapter.ProductAdapter
import com.turgayozdemir.getirlite.ui.adapter.SuggestedProductAdapter
import com.turgayozdemir.getirlite.ui.viewmodel.CartViewModel
import com.turgayozdemir.getirlite.ui.viewmodel.ProductViewModel
import com.turgayozdemir.getirlite.util.Resource

class BasketFragment : Fragment() {

    private var _binding: FragmentBasketBinding? = null
    private val binding get() = _binding!!

    private lateinit var suggestedProductAdapter: SuggestedProductAdapter
    private lateinit var cartAdapter: CartAdapter

    private lateinit var cartViewModel: CartViewModel
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = (activity as MainActivity).getCartViewModel()

        cartViewModel.totalPrice.observe(activity as MainActivity, Observer { total ->
            binding.basketTotalText.text = "₺${cartViewModel.formatNumber(total.toFloat(), false)}"
        })

        cartAdapter = CartAdapter(ArrayList())
        binding.basketRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.basketRecyclerView.adapter = cartAdapter

        cartViewModel.cartItems.observe(viewLifecycleOwner) { items ->
            cartAdapter.updateItems(items)
        }

        suggestedProduct()

    }

    private fun suggestedProduct(){
        productViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

        suggestedProductAdapter = SuggestedProductAdapter(ArrayList(),
            { product -> cartViewModel.addItemToCart(product) },
            { product -> cartViewModel.removeItemFromCart(product.id) },
            mapOf())
        binding.suggestedRecyclerView.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)
        binding.suggestedRecyclerView.adapter = suggestedProductAdapter

        productViewModel.suggestedProducts.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    suggestedProductAdapter.suggestedProductList.clear()
                    suggestedProductAdapter.suggestedProductList.addAll(resource.data!!)
                    suggestedProductAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                }
            }
        })

        cartViewModel.cartItems.observe(viewLifecycleOwner) { cartItems ->
            val cartMap = cartItems.associate { it.id to it.quantity }
            suggestedProductAdapter.updateCartItems(cartMap)
        }
    }

    private fun onAddItem(item: CartItem) {
        cartViewModel.addItemToCart(item)
    }

    private fun onRemoveItem(item: CartItem) {
        cartViewModel.removeItemFromCart(item.id)
    }

}