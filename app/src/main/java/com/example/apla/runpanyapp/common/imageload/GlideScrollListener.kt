package com.example.apla.runpanyapp.common.imageload

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Glide拡張用リスナー
 * スクロール中はimage取得を待機する（スクロール時のカクつきを抑える）
 *
 */

class GlideScrollListener(private val context: Context) : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(view: RecyclerView, scrollState: Int) {
        val glide = Glide.with(context)
        if (scrollState == RecyclerView.SCROLL_STATE_IDLE || scrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
            glide.resumeRequests()
        } else {
            glide.pauseRequests()
        }
    }
}