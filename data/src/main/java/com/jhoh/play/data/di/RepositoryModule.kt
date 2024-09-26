package com.jhoh.play.data.di

import com.jhoh.play.data.repository.PreferenceRepositoryImpl
import com.jhoh.play.data.repository.SearchRepositoryImpl
import com.jhoh.play.domain.repository.PreferenceRepository
import com.jhoh.play.domain.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchRepository(searchRepositoryImpl: SearchRepositoryImpl):
            SearchRepository = searchRepositoryImpl

    @Singleton
    @Provides
    fun providePreferenceRepository(preferenceRepositoryImpl: PreferenceRepositoryImpl):
            PreferenceRepository = preferenceRepositoryImpl
}