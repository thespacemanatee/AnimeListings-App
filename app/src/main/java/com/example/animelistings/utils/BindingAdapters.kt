package com.example.animelistings.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imageUrl: String?) {
    imageUrl?.let {
        GlideApp.with(imageView.context).load(imageUrl).into(imageView)
    }
}