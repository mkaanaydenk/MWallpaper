package com.mehmetkaanaydenk.mwallpaper.data.di

import com.mehmetkaanaydenk.mwallpaper.data.remote.WallpaperAPI
import com.mehmetkaanaydenk.mwallpaper.data.repository.WallpaperRepositoryImpl
import com.mehmetkaanaydenk.mwallpaper.domain.repository.WallpaperRepository
import com.mehmetkaanaydenk.mwallpaper.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWallpaperApi(): WallpaperAPI{

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WallpaperAPI::class.java)

    }

    @Provides
    @Singleton
    fun providesWallpaperRepository(api: WallpaperAPI): WallpaperRepository{
        return WallpaperRepositoryImpl(api = api)
    }

}