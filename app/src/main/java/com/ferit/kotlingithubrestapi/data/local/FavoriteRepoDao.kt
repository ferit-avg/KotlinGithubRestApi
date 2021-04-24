package com.ferit.kotlingithubrestapi.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface FavoriteRepoDao {
    @Query("SELECT * FROM favorite_repo")
    fun getFavoriteRepos(): LiveData<List<FavoriteRepo>>

    @Insert
    suspend fun addFavorite(favoriteRepo: FavoriteRepo)

    @Query("SELECT COUNT(*) FROM favorite_repo WHERE repoId = :repoId")
    suspend fun checkRepo(repoId: Int): Int

    @Query("DELETE FROM favorite_repo WHERE repoId = :repoId")
    suspend fun deleteFromFavorites(repoId: Int): Int

}