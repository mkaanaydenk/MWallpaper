package com.mehmetkaanaydenk.mwallpaper.domain.repository

import com.mehmetkaanaydenk.mwallpaper.data.remote.dto.WallpapersDto

interface WallpaperRepository {

    suspend fun getWallpapers(query: String?): WallpapersDto

    suspend fun getWallpaperDetail(id: String): WallpapersDto

}