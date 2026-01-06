package com.unicofrance.uniexo.ui.lib

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.BitmapDescriptor

@Composable
fun rememberMarkerIcon(
    @DrawableRes resId: Int
): BitmapDescriptor {
    val context = LocalContext.current
    return remember(resId) {
        context.bitmapDescriptorFromVector(resId)
    }
}