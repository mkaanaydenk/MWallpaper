package com.mehmetkaanaydenk.mwallpaper.data.remote.dto

import com.mehmetkaanaydenk.mwallpaper.domain.model.WallpaperDetail

data class WallpapersDetailDto(
    val collections: Int,
    val comments: Int,
    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    val user_id: Int,
    val views: Int,
    val webformatHeight: Int,
    val webformatURL: String,
    val webformatWidth: Int
)

fun WallpapersDetailDto.toWallpaperDetail(): WallpaperDetail{

    return WallpaperDetail(downloads, id, imageHeight, imageWidth, largeImageURL, likes, user, views)

}
