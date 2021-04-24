package com.ferit.kotlingithubrestapi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteRepo::class], version = 1)
abstract class FavoriteRepoDatabase: RoomDatabase() {
    abstract fun getFavoriteRepoDao(): FavoriteRepoDao
}