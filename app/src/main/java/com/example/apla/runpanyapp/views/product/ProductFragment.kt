package com.example.apla.runpanyapp.views.product

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.data.local.db.entities.CommentEntity
import com.example.apla.runpanyapp.data.local.db.entities.ProductEntity
import com.example.apla.runpanyapp.databinding.ProductFragmentBinding
import com.example.apla.runpanyapp.models.Comment
import com.example.apla.runpanyapp.viewmodels.product.ProductViewModel
import com.example.apla.runpanyapp.views.BaseFragment

class ProductFragment : BaseFragment() {

    private var mBinding: ProductFragmentBinding? = null

    private var mCommentAdapter: CommentAdapter? = null

    private val mCommentClickCallback = object : CommentClickCallback {
        override fun onClick(comment: Comment) {
            // no-op

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        // Inflate this data binding layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.product_fragment, container, false)
        // Create and set the adapter for the RecyclerView.
        mCommentAdapter = CommentAdapter(mCommentClickCallback)
        mBinding!!.commentList.setAdapter(mCommentAdapter)
        return mBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = ProductViewModel.Factory(
                activity!!.getApplication(), arguments!!.getInt(KEY_PRODUCT_ID))

        val model = ViewModelProviders.of(this, factory)
                .get(ProductViewModel::class.java)

        mBinding!!.productViewModel = model

        subscribeToModel(model)
    }

    private fun subscribeToModel(model: ProductViewModel) {

        // Observe product data
        model.observableProduct.observe(this, object : Observer<ProductEntity> {
            override fun onChanged(productEntity: ProductEntity?) {
                model.setProduct(productEntity!!)
            }
        })

        // Observe comments
        model.comments.observe(this, object : Observer<List<CommentEntity>> {
            override fun onChanged(commentEntities: List<CommentEntity>?) {
                if (commentEntities != null) {
                    mBinding!!.isLoading = false
                    mCommentAdapter!!.setCommentList(commentEntities)
                } else {
                    mBinding!!.isLoading = true
                }
            }
        })
    }

    companion object {

        private val KEY_PRODUCT_ID = "product_id"

        /** Creates product fragment for specific product ID  */
        fun forProduct(productId: Int): ProductFragment {
            val fragment = ProductFragment()
            val args = Bundle()
            args.putInt(KEY_PRODUCT_ID, productId)
            fragment.arguments = args
            return fragment
        }
    }
}
