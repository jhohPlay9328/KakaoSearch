package com.jhoh.play.data.mapper

import com.jhoh.play.domain.model.ImageModel
import com.jhoh.play.data.model.ImageRes
import com.jhoh.play.data.model.VideoRes
import com.jhoh.play.domain.model.VideoModel

fun mapperVideo(videoRes: VideoRes) = VideoModel(
    VideoModel.Meta(
        videoRes.meta.total_count,
        videoRes.meta.pageable_count,
        videoRes.meta.is_end,
    ),
    videoRes.documents.map { document ->
        VideoModel.Document(
            document.title,
            document.url,
            document.datetime,
            document.play_time,
            document.thumbnail,
            document.author,
        )
    }
)