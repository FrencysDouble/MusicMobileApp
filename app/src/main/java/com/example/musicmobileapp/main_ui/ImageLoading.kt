package com.example.musicmobileapp.main_ui

import android.content.Context
import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicmobileapp.R

object ImageLoading {

    @Composable
    fun loadImage(
        url: String,
        modifier: Modifier = Modifier,
        context: Context
    ) {
        AndroidView(
            modifier = modifier,
            factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP
                }
            },
            update = { imageView ->
                Glide.with(context)
                    .load(url)
                    .apply(RequestOptions().placeholder(R.drawable.spinner).error(R.drawable.baseline_error_outline_24))
                    .into(imageView)
            }
        )
    }
}
