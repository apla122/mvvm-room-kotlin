package com.example.apla.runpanyapp.views.product

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.data.local.db.entities.ProductEntity
import com.example.apla.runpanyapp.databinding.ProductListFragmentBinding
import com.example.apla.runpanyapp.models.Product
import com.example.apla.runpanyapp.viewmodels.product.ProductListViewModel
import com.example.apla.runpanyapp.views.BaseFragment

class ProductListFragment : BaseFragment() {

    private var mProductAdapter: ProductAdapter? = null

    private var mBinding: ProductListFragmentBinding? = null

    private val mProductClickCallback = object : ProductClickCallback {
        override fun onClick(product: Product) {

            if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
                (activity as ProductMainActivity).show(product)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.product_list_fragment, container, false)

        mProductAdapter = ProductAdapter(mProductClickCallback)
        mBinding!!.productsList.adapter = mProductAdapter

        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this).get(ProductListViewModel::class.java)

        mBinding!!.productsSearchBtn.setOnClickListener {
            _ ->
            val query = mBinding!!.productsSearchBox.text
            Log.d("TTT", query!!.toString())
            if (query == null || query!!.toString().isBlank()) {
                subscribeUi(viewModel.products)
            } else {
                subscribeUi(viewModel.searchProducts("*$query*"))
            }
        }

        subscribeUi(viewModel.products)
    }

    private fun subscribeUi(liveData: LiveData<List<ProductEntity>>) {
        // Update the list when the data changes
        liveData.observe(this, Observer<List<ProductEntity>> {
            myProducts ->
            if (myProducts != null) {
                mBinding!!.isLoading = false
                mProductAdapter!!.setProductList(myProducts)
            } else {
                mBinding!!.isLoading = true
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding!!.executePendingBindings()
        })
    }

    companion object {

        val TAG = "ProductListViewModel"
    }
}
