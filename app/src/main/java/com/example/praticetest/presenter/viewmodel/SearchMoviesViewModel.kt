package com.example.praticetest.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.praticetest.data.repository.MoviesRepository
import com.example.praticetest.data.vo.Movie
import com.example.praticetest.domain.constants.Constants
import com.example.praticetest.presenter.pagingsource.SearchMoviesPagingSource
import kotlinx.coroutines.flow.Flow
import java.net.URLEncoder

class SearchMoviesViewModel(private val repository: MoviesRepository) : ViewModel() {
    fun searchMovies(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                SearchMoviesPagingSource(repository = repository, encoder(query))
            }
        ).flow.cachedIn(viewModelScope)
    }

    private fun encoder(query: String): String {
        return URLEncoder.encode(query, "utf-8")
    }
}
