package com.jhoh.play.data.di

import android.content.Context
import com.jhoh.play.data.common.interceptor.BaseOkHttpInterceptor
import com.jhoh.play.data.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    companion object {
        // Retrofit 라이브러리를 사용하면 url 마지막에 "/"를 적어야 정상 동작함.

        const val KAKAO_API_KEY = "KakaoAK "
        private const val KAKAO_API_URL = "https://dapi.kakao.com/"

        private const val TIMEOUT_CONNECT = 3000L
        private const val TIMEOUT_READ = 3000L
        private const val TIMEOUT_WRITE = 3000L
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(baseOkHttpInterceptor: BaseOkHttpInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT_CONNECT, TimeUnit.MILLISECONDS)
            writeTimeout(TIMEOUT_READ, TimeUnit.MILLISECONDS)
            readTimeout(TIMEOUT_WRITE, TimeUnit.MILLISECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(baseOkHttpInterceptor)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    @Provides
    @Singleton
    @ApplicationContext
    fun provideBaseOkHttpInterceptor(context: Context): BaseOkHttpInterceptor {
        return BaseOkHttpInterceptor(context)
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(KAKAO_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }
}