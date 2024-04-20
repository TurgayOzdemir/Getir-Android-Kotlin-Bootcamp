package com.turgayozdemir.getirlite.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.turgayozdemir.getirlite.R
import com.turgayozdemir.getirlite.data.model.Product
import com.turgayozdemir.getirlite.data.model.SuggestedProduct
import com.turgayozdemir.getirlite.databinding.FragmentListingBinding
import com.turgayozdemir.getirlite.ui.adapter.ProductAdapter
import com.turgayozdemir.getirlite.ui.adapter.SuggestedProductAdapter
import com.turgayozdemir.getirlite.ui.viewmodel.ProductViewModel
import com.turgayozdemir.getirlite.util.Resource

class ListingFragment : Fragment() {

    private var _binding: FragmentListingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var suggestedProductAdapter: SuggestedProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as? MainActivity)?.PageTitle("Ürünler")
        (activity as? MainActivity)?.CloseIcon(false)

        // Initialize ViewModel
        viewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)

        // Initialize ProductAdapter
        productAdapter = ProductAdapter(ArrayList())
        binding.verticalRecyclerView.layoutManager = GridLayoutManager(context,3)
        binding.verticalRecyclerView.adapter = productAdapter

        suggestedProductAdapter = SuggestedProductAdapter(ArrayList())
        binding.horizontalRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        binding.horizontalRecyclerView.adapter = suggestedProductAdapter

        // Observe suggestedProduct data from ViewModel
        viewModel.suggestedProducts.observe(viewLifecycleOwner, { resource ->
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

        // Observe product data from ViewModel
        viewModel.products.observe(viewLifecycleOwner, { resource ->
            when (resource) {
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    productAdapter.productList.clear()
                    productAdapter.productList.addAll(resource.data!!)
                    productAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {

                }
            }
        })
    }

}