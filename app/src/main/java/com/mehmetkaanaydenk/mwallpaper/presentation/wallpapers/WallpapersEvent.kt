package com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers

sealed class WallpapersEvent {

    data class Search(val searchQuery: String) : WallpapersEvent()

}