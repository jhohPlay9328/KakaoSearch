package com.jhoh.play.data.datasource

import com.theenm.android.data.preference.PreferenceManager
import javax.inject.Inject

class PreferenceSource @Inject constructor(
    private val preferenceManager: PreferenceManager
){
    fun saveFavorite(favoriteJsonString: String) { preferenceManager.favoriteJsonString = favoriteJsonString }
    fun readFavorite() = preferenceManager.favoriteJsonString
}