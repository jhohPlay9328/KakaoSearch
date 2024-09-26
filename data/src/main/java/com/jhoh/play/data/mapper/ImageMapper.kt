package com.jhoh.play.data.mapper

import com.jhoh.play.domain.model.ImageModel
import com.jhoh.play.data.model.ImageRes

fun mapperImage(imageRes: ImageRes) = ImageModel(
    ImageModel.Meta(
        imageRes.meta.total_count,
        imageRes.meta.pageable_count,
        imageRes.meta.is_end,
    ),
    imageRes.documents.map { document ->
        ImageModel.Document(
            document.collection,
            document.thumbnail_url,
            document.image_url,
            document.width,
            document.height,
            document.display_sitename,
            document.doc_url,
            document.datetime,
        )
    }
)