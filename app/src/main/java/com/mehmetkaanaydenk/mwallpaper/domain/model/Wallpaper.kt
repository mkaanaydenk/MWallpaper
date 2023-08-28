package com.mehmetkaanaydenk.mwallpaper.domain.model

import com.mehmetkaanaydenk.mwallpaper.data.remote.dto.WallpapersDetailDto

data class Wallpaper(

    val id: Int,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,

)
