package com.jhoh.play.app.common.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object CommonBindingAdapter {
    @BindingAdapter("loadImage")
    @JvmStatic fun loadImage(imageView: AppCompatImageView, data: Any?) {
        Glide.with(imageView).load(data).into(imageView)
    }
}