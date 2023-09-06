package com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers.adapter.WallpapersRecyclerAdapter
import javax.inject.Inject

class MWallpaperFragmentFactory @Inject constructor(
    private val wallpapersRecyclerAdapter: WallpapersRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when(className){
            WallpapersFragment::class.java.name -> WallpapersFragment(wallpapersRecyclerAdapter)
            else -> super.instantiate(classLoader, className)

        }

    }

}