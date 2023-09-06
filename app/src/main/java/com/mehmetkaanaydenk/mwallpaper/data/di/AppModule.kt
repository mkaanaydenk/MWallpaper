package com.mehmetkaanaydenk.mwallpaper.data.di

import android.content.Context
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mehmetkaanaydenk.mwallpaper.R
import com.mehmetkaanaydenk.mwallpaper.data.remote.WallpaperAPI
import com.mehmetkaanaydenk.mwallpaper.data.repository.WallpaperRepositoryImpl
import com.mehmetkaanaydenk.mwallpaper.domain.repository.WallpaperRepository
import com.mehmetkaanaydenk.mwallpaper.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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



    fun placeHolderProgressBar(context: Context): CircularProgressDrawable {

        return CircularProgressDrawable(context).apply {
            strokeWidth = 8f
            centerRadius = 40f
            start()
        }
    }
    @Provides
    @Singleton
    fun injectGlide(@ApplicationContext context: Context)= Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(placeHolderProgressBar(context))
                .error(R.mipmap.ic_launcher_round)
                .transform(CenterCrop(), RoundedCorners(12))
        )



}


