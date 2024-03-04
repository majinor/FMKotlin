package com.daffamuhtar.fmkotlin.appv4.util

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter


object CustomBinding {
    @BindingAdapter("imageUrl")
    fun loadImage(imageView: ImageView, drawable: Drawable?) {
        if (drawable != null) {
            imageView.background = drawable
        }
    }
}