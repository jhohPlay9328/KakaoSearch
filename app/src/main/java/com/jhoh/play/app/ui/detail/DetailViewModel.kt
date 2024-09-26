package com.jhoh.play.app.ui.detail

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.jhoh.play.domain.model.MediaModel
import com.jhoh.play.app.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(
    application: Application
): BaseViewModel(application){
    private val _mediaModel = MutableStateFlow(MediaModel.Document())
    val mediaModel get() = _mediaModel

    fun setMediaModel(mediaModel: MediaModel.Document) {
        viewModelScope.launch {
            _mediaModel.emit(mediaModel)
        }
    }

    override suspend fun <T>resultUseCaseSuccess(callbackId: Int, data: T){}

    override suspend fun resultUseCaseFail(callbackId: Int, errorMessage: String){}
}