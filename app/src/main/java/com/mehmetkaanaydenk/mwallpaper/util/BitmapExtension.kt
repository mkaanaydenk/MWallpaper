package com.mehmetkaanaydenk.mwallpaper.util

import android.app.Activity
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Build
import android.util.DisplayMetrics

fun Bitmap.scaleAndSetWallpaper(activity: Activity, wallpaperManager: WallpaperManager) {


    val metrics = DisplayMetrics()
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val metrics = activity.windowManager.currentWindowMetrics
        Bitmap.createScaledBitmap(
            this,
            metrics.bounds.width(),
            metrics.bounds.height(),
            false
        )
        wallpaperManager.suggestDesiredDimensions(
            metrics.bounds.width(),
            metrics.bounds.height()
        )
        wallpaperManager.setWallpaperOffsetSteps(1F, 1F)
        wallpaperManager.setBitmap(this)
    } else {
        activity.windowManager.defaultDisplay.getMetrics(metrics)

        val phoneHeight = metrics.heightPixels
        val phoneWidth = metrics.widthPixels

        Bitmap.createScaledBitmap(this, phoneWidth, phoneHeight, true)
        wallpaperManager.setWallpaperOffsetSteps(1F, 1F)
        wallpaperManager.suggestDesiredDimensions(phoneWidth, phoneHeight)
        wallpaperManager.setBitmap(this)
    }


}