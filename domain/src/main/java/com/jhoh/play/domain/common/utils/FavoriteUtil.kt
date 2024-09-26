package com.jhoh.play.domain.common.utils

import com.jhoh.play.domain.model.MediaModel

object FavoriteUtil {
    fun isSameModel(item1: MediaModel.Document, item2: MediaModel.Document) =
        item1.thumbnail == item2.thumbnail &&
                item1.title == item2.title &&
                item1.category == item2.category &&
                item1.contents == item2.contents &&
                item1.time == item2.time &&
                item1.mediaType == item2.mediaType

}