package com.example.apla.runpanyapp.views.product

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.apla.runpanyapp.R
import com.example.apla.runpanyapp.databinding.ProductCommentItemBinding
import com.example.apla.runpanyapp.models.Comment

class CommentAdapter(private val mCommentClickCallback: CommentClickCallback) : androidx.recyclerview.widget.RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    private var mCommentList: List<Comment>? = null

    fun setCommentList(comments: List<Comment>) {
        if (mCommentList == null) {
            mCommentList = comments
            notifyItemRangeInserted(0, comments.size)
        } else {
            val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return mCommentList!!.size
                }

                override fun getNewListSize(): Int {
                    return comments.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = mCommentList!![oldItemPosition]
                    val comment = comments[newItemPosition]
                    return old.id === comment.id
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val old = mCommentList!![oldItemPosition]
                    val comment = comments[newItemPosition]
                    return (old.id === comment.id
                            && old.postedAt === comment.postedAt
                            && old.productId === comment.productId
                            && old.text == comment.text)
                }
            })
            mCommentList = comments
            diffResult.dispatchUpdatesTo(this)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.product_comment_item
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        val binding = DataBindingUtil.bind<ProductCommentItemBinding>(v)
        binding!!.callback = mCommentClickCallback
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.binding.comment = mCommentList!![position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return if (mCommentList == null) 0 else mCommentList!!.size
    }

    inner class CommentViewHolder(val binding: ProductCommentItemBinding) : androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root)

}