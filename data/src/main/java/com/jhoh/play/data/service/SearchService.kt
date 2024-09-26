package com.jhoh.play.data.service

import com.jhoh.play.data.model.ImageRes
import com.jhoh.play.data.model.VideoRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("v2/search/image")
    suspend fun requestImageData(
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): Response<ImageRes>

    @GET("v2/search/vclip")
    suspend fun requestVideoData(
        @Query("query") query: String,
        @Query("sort") sort: String?,
        @Query("page") page: Int?,
        @Query("size") size: Int?
    ): Response<VideoRes>
}