package com.example.apla.runpanyapp.common.utils

import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.squareup.picasso.Picasso

object ViewDataBindingAdapters {

    @JvmStatic
    @BindingAdapter("src")
    fun setImageViewResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (imageUrl.isNullOrEmpty()) {
            return
        }
        Picasso.with(view.context).load(imageUrl).fit().into(view)
    }

    @JvmStatic
    @BindingAdapter("onOkInSoftKeyboard") // I like it to match the listener method name.
    fun setOnOkInSoftKeyboardListener(view: TextView, listener: OnOkInSoftKeyboardListener?) {
        if (listener == null) {
            view.setOnEditorActionListener(null)
        } else {
            view.setOnEditorActionListener { v, actionId, event ->
                listener!!.onOkInSoftKeyboard(v)
                true
            }
        }
    }

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

}
