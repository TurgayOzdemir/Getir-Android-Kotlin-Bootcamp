package com.turgayozdemir.getirlite.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.turgayozdemir.getirlite.R
import com.turgayozdemir.getirlite.databinding.ActivityMainBinding
import com.turgayozdemir.getirlite.ui.viewmodel.CartViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private lateinit var cartViewModel: CartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        cartViewModel = ViewModelProvider(this@MainActivity).get(CartViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        binding.closeIcon.setOnClickListener {
            if (navController.currentDestination?.id == R.id.productDetailFragment) {
                navController.navigate(R.id.detail_to_listing)
            }
            if (navController.currentDestination?.id == R.id.basketFragment) {
                navController.navigate(R.id.basket_to_listing)
            }
        }

        binding.cartButton.setOnClickListener {
            if (navController.currentDestination?.id == R.id.listingFragment) {
                navController.navigate(R.id.listing_to_basket)
            }
            if (navController.currentDestination?.id == R.id.productDetailFragment) {
                navController.navigate(R.id.action_productDetailFragment_to_basketFragment)
            }
        }

        binding.basketTrashcan.setOnClickListener {
            cartViewModel.clearCart()
        }

        cartViewModel.totalPrice.observe(this, Observer { total ->
            binding.priceText.text = "₺${cartViewModel.formatNumber(total.toFloat(), true)}"
        })
    }

    fun getCartViewModel(): CartViewModel {
        return cartViewModel
    }

    fun CloseIcon(value : Boolean){
        if (value == true){
            binding.closeIcon.visibility = View.VISIBLE
        }
        else{
            binding.closeIcon.visibility = View.INVISIBLE
        }
    }

    fun CartButton(value : Boolean){
        if (value == true){
            binding.cartButton.visibility = View.VISIBLE
            binding.priceText.visibility = View.VISIBLE
            binding.basketTrashcan.visibility = View.INVISIBLE
        }
        else{
            binding.cartButton.visibility = View.INVISIBLE
            binding.priceText.visibility = View.INVISIBLE
            binding.basketTrashcan.visibility = View.VISIBLE
        }
    }

    fun PageTitle(value : String){
        binding.pageNameText.text = value
    }

    fun CartTotal(value: Float){
        binding.priceText.text = "₺$value"
    }




}