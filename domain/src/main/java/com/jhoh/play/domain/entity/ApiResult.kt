package com.jhoh.play.domain.entity

sealed class ApiResult {
    data class ResultSuccess<T>(val callbackId: Int, val data: T): ApiResult()
    data class ResultFail(val callbackId: Int, val errorMessage: String): ApiResult()
}