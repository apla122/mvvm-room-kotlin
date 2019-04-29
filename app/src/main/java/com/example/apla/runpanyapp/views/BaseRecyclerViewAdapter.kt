package com.example.apla.runpanyapp.views

import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.apla.runpanyapp.BaseApplication
import com.example.apla.runpanyapp.common.imageload.GlideScrollListener
import com.example.apla.runpanyapp.common.imageload.PicassoScrollListener
import com.example.apla.runpanyapp.models.BaseModel

open class BaseRecyclerViewAdapter<T : BaseModel> internal constructor(protected var mHolderLayout: Int, protected var mVariableId: Int, recyclerView: androidx.recyclerview.widget.RecyclerView, itemList: ObservableArrayList<T>) : androidx.recyclerview.widget.RecyclerView.Adapter<BaseRecyclerViewAdapter.BindingHolder>() {
    protected val GRID_VISIBLE_THRESHOLD = 10   // 残り件数 GridLayoutManager
    protected val LINE_VISIBLE_THRESHOLD = 5   // 残り件数 LinerLayoutManager
    protected var mItemList = ObservableArrayList<T>()
    protected lateinit var mCallbackListener: CallbackListener

    interface CallbackListener {
        fun onItemClick(v: View, position: Int)
        fun onLoadMore()
    }

    init {
        mItemList = itemList

        mItemList.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<T>>() {
            override fun onChanged(ts: ObservableList<T>) {
                notifyDataSetChanged()
            }

            override fun onItemRangeChanged(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
                notifyItemRangeChanged(positionStart, itemCount)
            }

            override fun onItemRangeInserted(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeMoved(sender: ObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableList<T>, positionStart: Int, itemCount: Int) {
                notifyItemRangeRemoved(positionStart, itemCount)
            }
        })

        val layoutManager = recyclerView.layoutManager
        if (layoutManager is androidx.recyclerview.widget.GridLayoutManager) {

            val gridLayoutManager = recyclerView.layoutManager as androidx.recyclerview.widget.GridLayoutManager

            recyclerView.addOnScrollListener(GlideScrollListener(BaseApplication.instance!!.getApplicationContext()))
            recyclerView.addOnScrollListener(PicassoScrollListener(BaseApplication.instance!!.getApplicationContext()))
            recyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView!!, dx, dy)
                    val totalItemCount = gridLayoutManager.itemCount                          // データ件数
                    val lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition()  // 現在表示中の最終アイテム位置
                    // データ残り件数が10件未満の場合、通知する

                    if (totalItemCount <= lastVisibleItemPosition + GRID_VISIBLE_THRESHOLD && totalItemCount > 0 && dy > 0) {
                        if (mCallbackListener != null) {
                            mCallbackListener!!.onLoadMore()
                        }
                    }
                }
            })
        } else if (layoutManager is androidx.recyclerview.widget.LinearLayoutManager) {
            val linearLayoutManager = recyclerView.layoutManager as androidx.recyclerview.widget.LinearLayoutManager
            recyclerView.addOnScrollListener(GlideScrollListener(BaseApplication.instance!!.getApplicationContext()))
            recyclerView.addOnScrollListener(PicassoScrollListener(BaseApplication.instance!!.getApplicationContext()))
            recyclerView.addOnScrollListener(object : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: androidx.recyclerview.widget.RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val totalItemCount = linearLayoutManager.itemCount                          // データ件数
                    val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()  // 現在表示中の最終アイテム位置
                    // データ残り件数が5件未満の場合、通知する
                    if (totalItemCount <= lastVisibleItemPosition + LINE_VISIBLE_THRESHOLD && totalItemCount > 0 && dy > 0) {
                        if (mCallbackListener != null) {
                            mCallbackListener!!.onLoadMore()
                        }
                    }
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewAdapter.BindingHolder {
        val v = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return BindingHolder(v)
    }

    override fun getItemViewType(position: Int): Int {
        return mHolderLayout
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewAdapter.BindingHolder, position: Int) {
        val item = mItemList[position]
        holder.binding.root.setOnClickListener { v ->
            if (mCallbackListener != null) {
                mCallbackListener!!.onItemClick(v, position)
            }
        }
        holder.binding.setVariable(mVariableId, item)
    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun setCallbackListener(onItemClickListener: CallbackListener) {
        this.mCallbackListener = onItemClickListener
    }

    open class BindingHolder(v: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        open val binding: ViewDataBinding

        init {
            binding = DataBindingUtil.bind(v)!!
        }
    }

}