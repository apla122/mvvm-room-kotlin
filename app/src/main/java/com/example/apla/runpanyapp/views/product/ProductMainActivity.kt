package com.example.apla.runpanyapp.views.product

import android.os.Bundle
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.models.Product
import com.example.apla.runpanyapp.views.BaseActivity

class ProductMainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.product_main_activity)

        // Add product list fragment if this is first creation
        if (savedInstanceState == null) {
            val fragment = ProductListFragment()

            supportFragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, ProductListFragment.TAG).commit()
        }
    }

    /** Shows the product detail fragment  */
    fun show(product: Product) {

        val productFragment = ProductFragment.forProduct(product.id)

        supportFragmentManager
                .beginTransaction()
                .addToBackStack("product")
                .replace(R.id.fragment_container,
                        productFragment, null).commit()
    }

}