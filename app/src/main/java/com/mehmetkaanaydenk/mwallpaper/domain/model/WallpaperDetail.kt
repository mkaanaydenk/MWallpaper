package com.mehmetkaanaydenk.mwallpaper.domain.model

import com.mehmetkaanaydenk.mwallpaper.data.remote.dto.WallpapersDetailDto

class WallpaperDetail(

    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val user: String,
    val views: Int

)