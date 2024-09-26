package com.jhoh.play.domain.repository

import com.jhoh.play.domain.entity.ApiResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun requestImageData(
        callbackId: Int,
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<ApiResult>

    suspend fun requestVideoData(
        callbackId: Int,
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<ApiResult>
}