package com.ferit.kotlingithubrestapi.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.ferit.kotlingithubrestapi.data.remote.api.SearchService
import javax.inject.Inject

class SearchRepository
@Inject
constructor(
    private val searchService: SearchService
){
    fun getUserAllRepos(username: String) = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { SearcPagingSource(searchService, username) }
    ).liveData
}