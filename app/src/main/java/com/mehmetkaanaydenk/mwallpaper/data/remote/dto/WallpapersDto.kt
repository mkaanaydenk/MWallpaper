package com.mehmetkaanaydenk.mwallpaper.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.mehmetkaanaydenk.mwallpaper.domain.model.Wallpaper

data class WallpapersDto(
    @SerializedName("hits")
    val wallpapersDetailDto: List<WallpapersDetailDto>,
    val total: Int,
    val totalHits: Int
)

fun WallpapersDto.toWallpaperList(): List<Wallpaper>{

    return wallpapersDetailDto.map { wallpapersDetailDto -> Wallpaper(
        wallpapersDetailDto.id,
        wallpapersDetailDto.previewHeight,
        wallpapersDetailDto.previewURL,
        wallpapersDetailDto.previewWidth,
    ) }

}
