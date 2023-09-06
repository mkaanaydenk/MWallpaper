package com.mehmetkaanaydenk.mwallpaper.domain.use_case.get_wallpaperDetail

import com.mehmetkaanaydenk.mwallpaper.data.remote.dto.toWallpaperDetail
import com.mehmetkaanaydenk.mwallpaper.domain.model.WallpaperDetail
import com.mehmetkaanaydenk.mwallpaper.domain.repository.WallpaperRepository
import com.mehmetkaanaydenk.mwallpaper.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError
import javax.inject.Inject

class GetWallpaperDetailUseCase @Inject constructor(private val repository: WallpaperRepository) {

    fun executeGetWallpaperDetails(id: String): Flow<Resource<WallpaperDetail>> = flow {

        try {

            emit(Resource.Loading())
            val wallpaper = repository.getWallpaperDetail(id)
            if (wallpaper.wallpapersDetailDto.isNotEmpty()){

                emit(Resource.Success(wallpaper.toWallpaperDetail()))

            }else{
                emit(Resource.Error("Wallpaper not found"))
            }

        }catch (e: IOError){
            emit(Resource.Error("Network error"))
        }

    }

}