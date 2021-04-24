package com.ferit.kotlingithubrestapi.data.local

import javax.inject.Inject

class FavoriteRepoRepository
@Inject constructor(
    private val favoriteRepoDao: FavoriteRepoDao
){
    fun getFavoriteRepos() = favoriteRepoDao.getFavoriteRepos()

    suspend fun addFavorite(favoriteRepo: FavoriteRepo) = favoriteRepoDao.addFavorite(favoriteRepo)

    suspend fun checkRepo(repoId: Int) = favoriteRepoDao.checkRepo(repoId)

    suspend fun deleteFromFavorites(repoId: Int) = favoriteRepoDao.deleteFromFavorites(repoId)
}