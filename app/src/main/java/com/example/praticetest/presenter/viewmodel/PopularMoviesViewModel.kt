package com.example.praticetest.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.praticetest.data.repository.MoviesRepository
import com.example.praticetest.data.vo.Movie
import com.example.praticetest.domain.constants.Constants.Companion.NETWORK_PAGE_SIZE
import com.example.praticetest.presenter.pagingsource.PopularMoviesPagingSource
import kotlinx.coroutines.flow.Flow

class PopularMoviesViewModel(private val repository: MoviesRepository) : ViewModel() {

    fun getPopularMovies() : Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PopularMoviesPagingSource(repository = repository)
            }
        ).flow.cachedIn(viewModelScope)
    }
}
