package com.mehmetkaanaydenk.mwallpaper.data.remote

import com.mehmetkaanaydenk.mwallpaper.data.remote.dto.WallpapersDto
import com.mehmetkaanaydenk.mwallpaper.util.Constants.API_KEY
import com.mehmetkaanaydenk.mwallpaper.util.Constants.IMAGE_TYPE
import com.mehmetkaanaydenk.mwallpaper.util.Constants.ORIENTATION
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale

interface WallpaperAPI {

    @GET("/api/")
    suspend fun getWallpapers(

        @Query("q") query: String?,
        @Query("key") apiKey: String = API_KEY,
        @Query("order") order: String = "trending",
        @Query("lang") lang: String = Locale.getDefault().language,
        @Query("category") category: String = "backgrounds",
        @Query("image_type") imageType: String = IMAGE_TYPE,
        @Query("orientation") orientation: String = ORIENTATION,
        @Query("per_page") perPage: Int = 180

    ): WallpapersDto

    @GET("/api/")
    suspend fun getWallpaperDetail(

        @Query("id") id: String,
        @Query("key") apiKey: String = API_KEY,
        @Query("image_type") imageType: String = IMAGE_TYPE,
        @Query("orientation") orientation: String = ORIENTATION,

    ): WallpapersDto

}