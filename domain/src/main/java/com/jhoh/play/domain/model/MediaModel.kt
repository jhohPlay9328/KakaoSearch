package com.jhoh.play.domain.model

import com.theenm.android.popcaster.common.constants.ApiConst
import java.io.Serializable
import java.util.Date


data class MediaModel(
    var meta: Meta = Meta(),
    var documents: MutableList<Document> = mutableListOf(),
) {
    data class Meta(
        var pageNum: Int = 1,
        var endPage: Boolean = false,
    ) {
        fun checkEndPage(maxPageNum: Int): Boolean = endPage || pageNum >= maxPageNum
    }

    data class Document(
        var thumbnail: String = "",
        var title: String = "",
        var category: String = "",
        var contents: String = "",
        var time: Date = Date(),
        var isFavorite: Boolean = false,
        var mediaType: Int = ApiConst.Param.MEDIA_TYPE_IMAGE
    ): Serializable
}


