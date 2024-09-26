package com.jhoh.play.domain.usecase

import com.jhoh.play.domain.common.utils.FavoriteUtil
import com.jhoh.play.domain.model.MediaModel
import com.jhoh.play.domain.repository.PreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val preferenceRepository: PreferenceRepository
) {
    fun saveFavorite(mediaModels: List<MediaModel.Document>) {
        preferenceRepository.saveFavorite(mediaModels)
    }
    fun readFavorite(): MutableList<MediaModel.Document> = preferenceRepository.readFavorite()

    fun addFavoriteItem(mediaModel: MediaModel.Document): Flow<MediaModel.Document> = flow {
        val favoriteList: MutableList<MediaModel.Document> = readFavorite()
        val isAdded: Boolean = favoriteList.add(mediaModel)
        if(isAdded) {
            mediaModel.isFavorite = true
            saveFavorite(favoriteList)
            emit(mediaModel)
        }
    }

    fun removeFavoriteItem(favoriteItem: MediaModel.Document): Flow<MediaModel.Document> = flow {
        val favoriteList: MutableList<MediaModel.Document> = readFavorite()
        val existIndex: Int = existIndex(favoriteItem)
        if(existIndex >= 0) {
            favoriteList.removeAt(existIndex)
            saveFavorite(favoriteList)
            emit(favoriteItem)
        }
    }

    fun isExistItem(favoriteItem: MediaModel.Document): Boolean{
        return existIndex(favoriteItem) >= 0
    }

    private fun existIndex(favoriteItem: MediaModel.Document): Int{
        val favoriteList: MutableList<MediaModel.Document> = readFavorite()
        return favoriteList.indexOfFirst { favorite ->
            FavoriteUtil.isSameModel(favorite, favoriteItem)
        }
    }
}