package com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail

import com.mehmetkaanaydenk.mwallpaper.domain.model.WallpaperDetail

data class WallpaperDetailState(

    val isLoading: Boolean = false,
    val wallpaperDetail: WallpaperDetail,
    val error: String = ""
)
