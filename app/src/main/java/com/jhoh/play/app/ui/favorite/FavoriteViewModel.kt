package com.jhoh.play.app.ui.favorite

import android.app.Application
import com.jhoh.play.app.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel@Inject constructor(
    application: Application
): BaseViewModel(application){
    fun readFavorite() = favoriteUseCase.readFavorite()

    override suspend fun <T>resultUseCaseSuccess(callbackId: Int, data: T){}

    override suspend fun resultUseCaseFail(callbackId: Int, errorMessage: String){}
}