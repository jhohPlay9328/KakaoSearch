package com.jhoh.play.data.common.util

import com.jhoh.play.domain.entity.ApiResult
import retrofit2.Response
import timber.log.Timber


object RetrofitUtil {
    inline fun <reified RESPONSE, reified MODEL> mappingResponse(
        callbackId: Int,
        response: Response<RESPONSE>,
        mapper: (response: RESPONSE) -> MODEL
    ): ApiResult {
        return when(response.isSuccessful) {
            true -> {
                val responseBody = response.body()

//                Timber.d("Search Test RetrofitUtil responseEmit callbackId: $callbackId / responseBody : $responseBody")
                responseBody?.let {
                    val mapperModel = mapper(responseBody)
                    ApiResult.ResultSuccess(callbackId, mapperModel)
                }?: ApiResult.ResultFail(callbackId, "ResponseBody is Empty")
            }
            else -> {
                val errorBody = response.errorBody()?.source().toString()
                ApiResult.ResultFail(callbackId, errorBody)
            }
        }
    }
}
