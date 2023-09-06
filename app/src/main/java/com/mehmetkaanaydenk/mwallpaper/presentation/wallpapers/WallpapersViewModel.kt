package com.mehmetkaanaydenk.mwallpaper.presentation.wallpapers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetkaanaydenk.mwallpaper.domain.use_case.get_wallpapers.GetWallpapersUseCase
import com.mehmetkaanaydenk.mwallpaper.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WallpapersViewModel @Inject constructor(private val getWallpapersUseCase: GetWallpapersUseCase): ViewModel() {

    private val _state = MutableStateFlow<WallpapersState>(WallpapersState())
    val state : StateFlow<WallpapersState> = _state


    private var job: Job? = null

    init {
        getWallpapers(_state.value.query)
    }

    private fun getWallpapers(query: String){
        job?.cancel()

        job = getWallpapersUseCase.executeGetWallpapers(query = query).onEach {

           when(it){

               is Resource.Success -> {

                   _state.value = WallpapersState(wallpapers = it.data ?: emptyList())

               }
               is Resource.Error -> {

                   _state.value = WallpapersState(error = it.message ?: "Error")

               }
               is Resource.Loading -> {

                   _state.value = WallpapersState(isLoading = true)

               }

           }

        }.launchIn(viewModelScope)

    }

    fun onEvent(event: WallpapersEvent){

        when(event){

            is WallpapersEvent.Search -> {

                getWallpapers(event.searchQuery)

            }

        }

    }

}