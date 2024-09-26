package com.jhoh.play.data.common.interceptor

import android.content.Context
import com.jhoh.play.data.BuildConfig
import com.jhoh.play.data.di.NetworkModule
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseOkHttpInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
): Interceptor {
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader(
                "Authorization",
                "${NetworkModule.KAKAO_API_KEY}${BuildConfig.KAKAO_API_KEY}"
            )
            .build()
        proceed(newRequest)
    }
}