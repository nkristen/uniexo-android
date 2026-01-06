package com.unicofrance.uniexo.ui.lib

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

fun Context.bitmapDescriptorFromVector(
    @DrawableRes resId: Int,
    sizeDp: Dp = 32.dp
): BitmapDescriptor {
    val drawable = AppCompatResources.getDrawable(this, resId)
        ?: error("Drawable not found")

    val sizePx = with(resources.displayMetrics) {
        (sizeDp.value * density).toInt()
    }

    val bitmap = Bitmap.createBitmap(sizePx, sizePx, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}
