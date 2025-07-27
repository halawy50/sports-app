package com.example.myapplication.presentation.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.decode.GifDecoder

@Composable
fun GifFromDrawable(context: Context, gifImage: Int) {
    // استخدام ImageRequest لعرض GIF بحجم كامل
    val imageLoader = ImageLoader.Builder(context)
        .components { add(GifDecoder.Factory()) } // دعم الـ GIF
        .build()

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(gifImage) // اسم ملف الـ GIF في drawable
            .build(),
        contentDescription = "Animated GIF",
        modifier = Modifier.fillMaxSize(), // ملء كامل الشاشة
        contentScale = ContentScale.Crop, // تأكد من أن الـ GIF سيملأ الشاشة تمامًا
        imageLoader = imageLoader // تمرير ImageLoader المخصص
    )
}
