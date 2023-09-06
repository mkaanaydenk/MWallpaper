package com.mehmetkaanaydenk.mwallpaper.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.mehmetkaanaydenk.mwallpaper.domain.model.Wallpaper
import com.mehmetkaanaydenk.mwallpaper.domain.model.WallpaperDetail

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

fun WallpapersDto.toWallpaperDetail(): WallpaperDetail{

    return WallpaperDetail(
        wallpapersDetailDto[0].downloads,
        wallpapersDetailDto[0].id,
        wallpapersDetailDto[0].imageHeight,
        wallpapersDetailDto[0].imageWidth,
        wallpapersDetailDto[0].largeImageURL,
        wallpapersDetailDto[0].likes,
        wallpapersDetailDto[0].user,
        wallpapersDetailDto[0].views
    )

}
