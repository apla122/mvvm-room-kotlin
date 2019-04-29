package com.example.apla.runpanyapp.common.imageload

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

/**
 * Picasso拡張用リスナー
 * スクロール中はimage取得を待機する（スクロール時のカクつきを抑える）
 *
 */

class PicassoScrollListener(private val context: Context) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(view: RecyclerView, scrollState: Int) {
        val picasso = Picasso.with(context)
        if (scrollState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE || scrollState == androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING) {
            picasso.resumeTag(context)
        } else {
            picasso.pauseTag(context)
        }
    }
}