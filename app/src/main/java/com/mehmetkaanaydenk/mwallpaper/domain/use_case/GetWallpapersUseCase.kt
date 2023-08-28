package com.mehmetkaanaydenk.mwallpaper.domain.use_case

import com.mehmetkaanaydenk.mwallpaper.data.remote.dto.toWallpaperList
import com.mehmetkaanaydenk.mwallpaper.domain.model.Wallpaper
import com.mehmetkaanaydenk.mwallpaper.domain.repository.WallpaperRepository
import com.mehmetkaanaydenk.mwallpaper.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError
import javax.inject.Inject

class GetWallpapersUseCase @Inject constructor(private val repository: WallpaperRepository) {


    fun executeGetWallpapers(query: String?): Flow<Resource<List<Wallpaper>>> = flow{

        try {

            emit(Resource.Loading())
            val wallpapers = repository.getWallpapers(query)
            if (wallpapers.wallpapersDetailDto.isNotEmpty()){
                emit(Resource.Success(wallpapers.toWallpaperList()))
            }else{
                emit(Resource.Error("No wallpaper found"))
            }

        }catch (e: IOError){
            emit(Resource.Error("connection error"))
        }

    }


}