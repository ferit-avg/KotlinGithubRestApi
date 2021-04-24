package com.ferit.kotlingithubrestapi.ui.menu.home.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.ferit.kotlingithubrestapi.data.remote.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject constructor(
    private val searchRepository: SearchRepository,
    private val savedStateHandle: SavedStateHandle
): ViewModel(), LifecycleObserver {
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val repos = currentQuery.switchMap { username ->
        searchRepository.getUserAllRepos(username = username ).cachedIn(viewModelScope)
    }

    fun searchRepos(username: String) {
        currentQuery.value = username
    }

    companion object {
        private const val DEFAULT_QUERY = "google"
    }
}