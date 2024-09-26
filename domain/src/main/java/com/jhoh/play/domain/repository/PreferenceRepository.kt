package com.jhoh.play.domain.repository

import com.jhoh.play.domain.model.MediaModel

interface PreferenceRepository {
    fun saveFavorite(mediaModels: List<MediaModel.Document>)
    fun readFavorite(): MutableList<MediaModel.Document>
}