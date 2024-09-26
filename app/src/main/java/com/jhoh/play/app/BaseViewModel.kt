package com.jhoh.play.app

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.jhoh.play.domain.entity.ApiResult
import com.jhoh.play.domain.usecase.FavoriteUseCase
import com.jhoh.play.domain.usecase.SearchUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

abstract class BaseViewModel(
    application: Application,
): AndroidViewModel(application) {
    private val context get() = getApplication<Application>()

    @Inject internal lateinit var searchUseCase: SearchUseCase
    @Inject lateinit var favoriteUseCase: FavoriteUseCase

    private val _showApiErrorPopup = MutableSharedFlow<String>()
    val showApiErrorPopup get() = _showApiErrorPopup

    fun showApiErrorPopup(errorMessage: String) {
        viewModelScope.launch {
            _showApiErrorPopup.emit(errorMessage)
        }
    }

    protected open fun clearMediaData(callbackId: Int, ){
        viewModelScope.launch  {
            viewModelScope.launch  {
                searchUseCase.clearMediaData(callbackId).collect {
                    resultUseCase(it)
                }
            }
        }
    }

    protected open fun requestMediaData(
        callbackId: Int,
        query: String,
        isRefresh: Boolean
    ){
        viewModelScope.launch  {
            searchUseCase.requestMediaData(
                callbackId,
                query,
                isRefresh
            ).collect {
                resultUseCase(it)
            }
        }
    }

    private suspend fun resultUseCase(apiResult: ApiResult){
        when (apiResult) {
            is ApiResult.ResultSuccess<*> -> {
                resultUseCaseSuccess(apiResult.callbackId, apiResult.data)
            }
            is ApiResult.ResultFail -> {
                resultUseCaseFail(apiResult.callbackId, apiResult.errorMessage)
            }
            else -> {}
        }
    }

    abstract suspend fun <T>resultUseCaseSuccess(callbackId: Int, data: T)
    abstract suspend fun resultUseCaseFail(callbackId: Int, errorMessage: String)
}