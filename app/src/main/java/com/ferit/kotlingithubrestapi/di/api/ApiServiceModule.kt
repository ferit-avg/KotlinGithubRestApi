package com.ferit.kotlingithubrestapi.di.api

import com.ferit.kotlingithubrestapi.data.remote.api.SearchService
import com.ferit.kotlingithubrestapi.di.network.AppServiceRetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Singleton
    @Provides
    fun provideSearchService(@AppServiceRetrofitInstance retrofit: Retrofit): SearchService {
        return retrofit.create(SearchService::class.java)
    }
}