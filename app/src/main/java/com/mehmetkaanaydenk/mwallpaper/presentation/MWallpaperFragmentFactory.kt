package com.mehmetkaanaydenk.mwallpaper.presentation

import android.app.WallpaperManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.mehmetkaanaydenk.mwallpaper.domain.model.WallpaperDetail
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpaper_detail.view.WallpaperDetailFragment
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.adapter.WallpapersRecyclerAdapter
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.view.WallpapersFragment
import javax.inject.Inject

class MWallpaperFragmentFactory @Inject constructor(
    private val wallpapersRecyclerAdapter: WallpapersRecyclerAdapter,
    private val glide: RequestManager,
    private val wallpaperManager: WallpaperManager
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            WallpapersFragment::class.java.name -> WallpapersFragment(wallpapersRecyclerAdapter)
            WallpaperDetailFragment::class.java.name -> WallpaperDetailFragment(glide,wallpaperManager)
            else -> super.instantiate(classLoader, className)

        }

    }

}