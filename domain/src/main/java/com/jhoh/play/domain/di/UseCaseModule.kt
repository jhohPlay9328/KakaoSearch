package com.jhoh.play.domain.di

import com.jhoh.play.domain.repository.PreferenceRepository
import com.jhoh.play.domain.repository.SearchRepository
import com.jhoh.play.domain.usecase.FavoriteUseCase
import com.jhoh.play.domain.usecase.SearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideSearchUseCase(
        searchRepository: SearchRepository,
        preferenceRepository: PreferenceRepository
    ) = SearchUseCase(searchRepository, preferenceRepository)

    @Singleton
    @Provides
    fun provideFavoriteUseCase(
        preferenceRepository: PreferenceRepository
    ) = FavoriteUseCase(preferenceRepository)
}