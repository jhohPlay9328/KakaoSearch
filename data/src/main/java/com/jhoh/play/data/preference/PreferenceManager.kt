package com.theenm.android.data.preference

import android.content.SharedPreferences
import javax.inject.Inject


class PreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
){
    companion object{
        private const val FAVORITE_JSON_STRING = "FAVORITE_JSON_STRING"

    }

    var favoriteJsonString
        get() = sharedPreferences.getString(FAVORITE_JSON_STRING, "")
        set(value) {
            sharedPreferences.edit().putString(FAVORITE_JSON_STRING, value).apply()
        }
}