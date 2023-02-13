package com.alancamargo.tubecalculator.core.design.extensions

import android.os.Build
import android.widget.ImageView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import coil.size.Size

fun ImageView.loadGif(gifBytes: ByteArray) {
    val imageLoader = ImageLoader.Builder(context).components {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            add(ImageDecoderDecoder.Factory())
        } else {
            add(GifDecoder.Factory())
        }
    }.build()

    load(gifBytes, imageLoader) {
        size(Size.ORIGINAL)
    }
}
