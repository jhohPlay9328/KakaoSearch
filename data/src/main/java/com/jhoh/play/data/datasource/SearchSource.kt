package com.jhoh.play.data.datasource

import com.jhoh.play.data.model.ImageRes
import com.jhoh.play.data.model.VideoRes
import com.jhoh.play.data.service.SearchService
import retrofit2.Response
import javax.inject.Inject

class SearchSource @Inject constructor(
    private val searchService: SearchService
){
    suspend fun requestImageData(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): Response<ImageRes> {
        return searchService.requestImageData(query, sort, page, size)
    }

    suspend fun requestVideoData(
        query: String,
        sort: String,
        page: Int,
        size: Int,
    ): Response<VideoRes> {
        return searchService.requestVideoData(query, sort, page, size)
    }
}