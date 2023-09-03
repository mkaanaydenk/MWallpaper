package com.mehmetkaanaydenk.mwallpaper.data.remote

import com.mehmetkaanaydenk.mwallpaper.data.remote.dto.WallpapersDto
import com.mehmetkaanaydenk.mwallpaper.util.Constants.API_KEY
import com.mehmetkaanaydenk.mwallpaper.util.Constants.IMAGE_TYPE
import com.mehmetkaanaydenk.mwallpaper.util.Constants.ORIENTATION
import retrofit2.http.GET
import retrofit2.http.Query

interface WallpaperAPI {

    @GET("/api/")
    suspend fun getWallpapers(

        @Query("q") query: String?,
        @Query("key") apiKey: String = API_KEY,
        @Query("image_type") imageType: String = IMAGE_TYPE,
        @Query("orientation") orientation: String = ORIENTATION

    ): WallpapersDto

}