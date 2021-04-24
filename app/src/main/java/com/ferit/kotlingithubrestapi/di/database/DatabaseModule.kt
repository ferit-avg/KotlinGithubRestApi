package com.ferit.kotlingithubrestapi.di.database

import android.content.Context
import androidx.room.Room
import com.ferit.kotlingithubrestapi.data.local.FavoriteRepoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideFavoriteRepoDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        FavoriteRepoDatabase::class.java,
        "favorite_repo_db"
    ).build()

    @Singleton
    @Provides
    fun provideFavoriteRepoDao(database: FavoriteRepoDatabase) = database.getFavoriteRepoDao()
}