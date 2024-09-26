package com.jhoh.play.app

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.jhoh.play.domain.model.MediaModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
): BaseViewModel(application){
    private val _addFavoriteItem = MutableSharedFlow<MediaModel.Document>()
    val addFavoriteItem get() = _addFavoriteItem

    private val _removeFavoriteItem = MutableSharedFlow<MediaModel.Document>()
    val removeFavoriteItem get() = _removeFavoriteItem

    private fun addFavoriteItem(favoriteItem: MediaModel.Document) {
        viewModelScope.launch {
            favoriteUseCase.addFavoriteItem(favoriteItem).collect { favoriteItem ->
                _addFavoriteItem.emit(favoriteItem)
            }
        }
    }

    private fun removeFavoriteItem(favoriteItem: MediaModel.Document) {
        viewModelScope.launch {
            favoriteUseCase.removeFavoriteItem(favoriteItem).collect { favoriteItem ->
                _removeFavoriteItem.emit(favoriteItem)
            }
        }
    }

    fun changeFavoriteItem(favoriteItem: MediaModel.Document) {
        when(favoriteUseCase.isExistItem(favoriteItem)) {
            true -> removeFavoriteItem(favoriteItem)
            false -> addFavoriteItem(favoriteItem)
        }
    }

    override suspend fun <T> resultUseCaseSuccess(callbackId: Int, data: T) {}

    override suspend fun resultUseCaseFail(callbackId: Int, errorMessage: String) {}
}