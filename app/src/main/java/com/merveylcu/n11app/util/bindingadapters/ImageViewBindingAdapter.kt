package com.merveylcu.n11app.util.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.merveylcu.n11app.R

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String?) {
    if (!url.isNullOrEmpty()) {
        Glide.with(view.context).load(url).into(view)
    }
}

@BindingAdapter("isFavorite")
fun setFavorite(view: ImageView, isFavorite: Boolean?) {
    view.setImageResource(if (isFavorite == true) R.drawable.ic_favorited else R.drawable.ic_not_favorited)
}