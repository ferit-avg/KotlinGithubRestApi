package com.ferit.kotlingithubrestapi.ui.menu.favorites.viewmodel

import androidx.lifecycle.ViewModel
import com.ferit.kotlingithubrestapi.data.local.FavoriteRepoRepository
import com.ferit.kotlingithubrestapi.data.local.FavoriteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteReposViewModel
@Inject constructor(
    private val repository: FavoriteRepoRepository
): ViewModel() {

    val getFavoriteRepos = repository.getFavoriteRepos()

    fun addFavorite(favoriteRepo: FavoriteRepo) = CoroutineScope(Dispatchers.IO).launch {
        repository.addFavorite(favoriteRepo)
    }

    suspend fun checkRepo(repoId: Int) = repository.checkRepo(repoId)

    fun deleteFromFavorites(repoId: Int) = CoroutineScope(Dispatchers.IO).launch {
        repository.deleteFromFavorites(repoId)
    }

}