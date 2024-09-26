package com.jhoh.play.data.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jhoh.play.data.datasource.PreferenceSource
import com.jhoh.play.domain.model.MediaModel
import com.jhoh.play.domain.repository.PreferenceRepository
import javax.inject.Inject


class PreferenceRepositoryImpl @Inject constructor(
    private val preferenceSource: PreferenceSource,
): PreferenceRepository {
    override fun saveFavorite(mediaModels: List<MediaModel.Document>) {
        preferenceSource.saveFavorite(Gson().toJson(mediaModels))
    }

    override fun readFavorite(): MutableList<MediaModel.Document> =
        when(preferenceSource.readFavorite()?.isNotEmpty()) {
            true -> Gson().fromJson(
                preferenceSource.readFavorite(),
                object : TypeToken<MutableList<MediaModel.Document>>() {}.type
            )
            else -> arrayListOf()
        }
}