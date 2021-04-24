package com.ferit.kotlingithubrestapi.di.network.okhttp

import com.ferit.kotlingithubrestapi.di.network.apikey.AppServiceApiKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpModule {

    @Provides
    fun getInterceptor(apiKeyHeader: String): Interceptor {
        return Interceptor { chain ->
            val newUrl = chain.request().url
                .newBuilder()
                .addQueryParameter("api_key", apiKeyHeader)
                .build()

            val newRequest = chain.request()
                .newBuilder()
                .url(newUrl)
                .addHeader("Content-Type", "application/json")
                .build()

            chain.proceed(newRequest)
        }
    }

    @AppServiceInterceptorOkHttpClient
    @Singleton
    @Provides
    fun provideAppServiceInterceptorOkHttpClient(@AppServiceApiKey apiKeyHeader: String): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(getInterceptor(apiKeyHeader))
            .addInterceptor(httpLoggingInterceptor)
            .callTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
}