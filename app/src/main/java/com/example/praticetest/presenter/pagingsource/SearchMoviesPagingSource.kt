package com.example.praticetest.presenter.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.praticetest.data.repository.MoviesRepository
import com.example.praticetest.data.vo.Movie
import com.example.praticetest.domain.constants.Constants.Companion.STARTING_PAGE
import com.example.praticetest.domain.model.ExceptionModel
import com.example.praticetest.domain.model.NetworkResult

class SearchMoviesPagingSource(private val repository: MoviesRepository, private val query: String)
    : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        try {
            val response = repository.searchMovies(params.key ?: STARTING_PAGE, query)
            return when (response) {
                is NetworkResult.Success -> {
                    LoadResult.Page(
                        data = response.data.movies,
                        prevKey = params.key,
                        nextKey = if (response.data.movies.isEmpty()) {
                            null
                        } else {
                            params.key?.plus(1) ?: STARTING_PAGE.plus(1)
                        }
                    )
                }
                is NetworkResult.Error -> {
                    LoadResult.Error(ExceptionModel())
                }
            }
        } catch (exception: Throwable) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPostion ->
            state.closestPageToPosition(anchorPostion)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPostion)?.nextKey?.minus(1)

        }
    }
}
