package com.jhoh.play.data.repository

import com.jhoh.play.data.common.util.RetrofitUtil
import com.jhoh.play.data.datasource.SearchSource
import com.jhoh.play.data.mapper.mapperImage
import com.jhoh.play.data.mapper.mapperVideo
import com.jhoh.play.data.model.ImageRes
import com.jhoh.play.data.model.VideoRes
import com.jhoh.play.domain.model.ImageModel
import com.jhoh.play.domain.model.VideoModel
import com.jhoh.play.domain.repository.SearchRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class SearchRepositoryImpl @Inject constructor(
    private val searchSource: SearchSource,
): SearchRepository {
    override suspend fun requestImageData(
        callbackId: Int,
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ) = flow {
        searchSource.requestImageData(
            query,
            sort,
            page,
            size
        ).apply {
            RetrofitUtil.mappingResponse<ImageRes, ImageModel>(
                callbackId,
                this,
                ::mapperImage
            ).apply {
                emit(this)
            }
        }
    }

    override suspend fun requestVideoData(
        callbackId: Int,
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ) = flow {
        searchSource.requestVideoData(
            query,
            sort,
            page,
            size
        ).apply {
            RetrofitUtil.mappingResponse<VideoRes, VideoModel>(
                callbackId,
                this,
                ::mapperVideo
            ).apply {
                emit(this)
            }
        }
    }
}