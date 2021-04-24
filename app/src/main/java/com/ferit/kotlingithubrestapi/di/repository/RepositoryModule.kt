package com.ferit.kotlingithubrestapi.di.repository

import com.ferit.kotlingithubrestapi.data.remote.api.SearchService
import com.ferit.kotlingithubrestapi.data.remote.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideSearchRepository(searchService: SearchService): SearchRepository {
        return SearchRepository(searchService)
    }
}