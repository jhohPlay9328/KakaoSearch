package com.jhoh.play.app.ui.search

import android.app.Application
import com.jhoh.play.app.BaseViewModel
import com.jhoh.play.domain.model.MediaModel
import com.theenm.android.popcaster.common.constants.ApiConst
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(
    application: Application
): BaseViewModel(application){
    private val _searchMediaData = MutableStateFlow(MediaModel())
    val searchMediaData get() = _searchMediaData

    private val _clearMediaData = MutableSharedFlow<Boolean>()
    val clearMediaData get() = _clearMediaData

    fun requestMediaData(
        query: String,
        isRefresh: Boolean
    ) {
        super.requestMediaData(
            ApiConst.CallbackId.MEDIA_SEARCH,
            query,
            isRefresh
        )
    }

    fun clearMediaData() {
        super.clearMediaData(ApiConst.CallbackId.MEDIA_CLEAR)
    }

    override suspend fun <T>resultUseCaseSuccess(callbackId: Int, data: T){
        when(callbackId) {
            ApiConst.CallbackId.MEDIA_SEARCH -> _searchMediaData.emit(data as MediaModel)
            ApiConst.CallbackId.MEDIA_CLEAR -> _clearMediaData.emit(data as Boolean)
        }
    }

    override suspend fun resultUseCaseFail(callbackId: Int, errorMessage: String){
        showApiErrorPopup(errorMessage)
    }
}