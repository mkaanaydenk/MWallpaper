package com.mehmetkaanaydenk.mwallpaper.data.repository

import com.mehmetkaanaydenk.mwallpaper.data.remote.WallpaperAPI
import com.mehmetkaanaydenk.mwallpaper.data.remote.dto.WallpapersDto
import com.mehmetkaanaydenk.mwallpaper.domain.repository.WallpaperRepository
import javax.inject.Inject

class WallpaperRepositoryImpl @Inject constructor(private val api: WallpaperAPI): WallpaperRepository {
    override suspend fun getWallpapers(query: String?): WallpapersDto {
        return api.getWallpapers(query = query)
    }
}