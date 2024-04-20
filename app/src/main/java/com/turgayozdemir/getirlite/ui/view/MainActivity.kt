package com.turgayozdemir.getirlite.ui.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.turgayozdemir.getirlite.R
import com.turgayozdemir.getirlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
        }
    }

    fun CloseIcon(value : Boolean){
        if (value == true){
            binding.closeIcon.visibility = View.VISIBLE
        }
        else{
            binding.closeIcon.visibility = View.INVISIBLE
        }
    }

    fun PageTitle(value : String){
        binding.pageNameText.text = value
    }

    fun CartTotal(value: Float){
        binding.priceText.text = "₺$value"
    }


}