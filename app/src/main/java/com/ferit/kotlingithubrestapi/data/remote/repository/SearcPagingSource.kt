package com.ferit.kotlingithubrestapi.data.remote.repository

import androidx.paging.PagingSource
import com.ferit.kotlingithubrestapi.data.remote.api.SearchService
import com.ferit.kotlingithubrestapi.data.model.repo.Repo
import okio.IOException
import retrofit2.HttpException

class SearcPagingSource(
    private val searchService: SearchService,
    private val username: String
): PagingSource<Int, Repo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val pageNumber = params.key ?: INITIAL_PAGE

        return try {
            val response = searchService.getUserAllRepos(username = username, page = pageNumber)
            val movies = response

            LoadResult.Page(
                data = movies,
                prevKey = if (pageNumber == INITIAL_PAGE) null else pageNumber -1,
                nextKey = if (movies.isEmpty()) null else pageNumber + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE = 1
    }
}