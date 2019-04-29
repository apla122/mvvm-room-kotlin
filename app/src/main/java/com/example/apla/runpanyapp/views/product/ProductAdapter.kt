package com.example.apla.runpanyapp.views.product

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.databinding.ProductItemBinding
import com.example.apla.runpanyapp.models.Product

class ProductAdapter(private val mProductClickCallback: ProductClickCallback) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    var mProductList: List<Product>? = null

    init {
        setHasStableIds(true)
    }

    fun setProductList(productList: List<Product>) {
        if (mProductList == null) {
            mProductList = productList
            notifyItemRangeInserted(0, productList.size)
        } else {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mProductList!!.size
                }

                override fun getNewListSize(): Int {
                    return productList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mProductList!![oldItemPosition].id === productList[newItemPosition].id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newProduct = productList[newItemPosition]
                    val oldProduct = mProductList!![oldItemPosition]
                    return (newProduct.id === oldProduct.id
                            && newProduct.description == oldProduct.description
                            && newProduct.name == oldProduct.name
                            && newProduct.price === oldProduct.price)
                }
            })
            mProductList = productList
            result.dispatchUpdatesTo(this)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.product_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        val binding = DataBindingUtil.bind<ProductItemBinding>(v)
        binding!!.callback = mProductClickCallback
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.binding.product = mProductList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemId(position: Int): Long {
        return mProductList!![position].id.toLong()
    }

    override fun getItemCount(): Int {
        return if (mProductList == null) 0 else mProductList!!.size
    }

    inner class ProductViewHolder(val binding: ProductItemBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

}