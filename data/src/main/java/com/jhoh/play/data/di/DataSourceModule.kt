package com.jhoh.play.data.di

import com.jhoh.play.data.datasource.PreferenceSource
import com.jhoh.play.data.datasource.SearchSource
import com.jhoh.play.data.service.SearchService
import com.theenm.android.data.preference.PreferenceManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Singleton
    @Provides
    fun provideSearchSource(searchService: SearchService) = SearchSource(searchService)

    @Singleton
    @Provides
    fun providePreferenceSource(
        preferenceManager: PreferenceManager
    ) = PreferenceSource(preferenceManager)
}