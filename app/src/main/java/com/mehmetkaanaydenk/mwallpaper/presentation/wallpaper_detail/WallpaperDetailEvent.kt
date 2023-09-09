package com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail

sealed class WallpaperDetailEvent {

    data class GetById(val id: String): WallpaperDetailEvent()

}