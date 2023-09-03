package com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers

import com.mehmetkaanaydenk.mwallpaper.domain.model.Wallpaper

data class WallpapersState(

    val isLoading : Boolean = false,
    val wallpapers : List<Wallpaper> = emptyList(),
    val error : String = "",
    val query : String = ""

)
