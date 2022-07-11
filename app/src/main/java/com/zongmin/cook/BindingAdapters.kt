package com.zongmin.cook

import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        GlideApp.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
            )
            .into(imgView)
    }
}

@BindingConversion
fun convertColorToDrawable(color: Int) : ColorDrawable {
    return ColorDrawable(color)
}