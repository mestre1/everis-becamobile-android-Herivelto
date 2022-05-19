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
import com.example.praticetest.presenter.pagingsource.UpcomingMoviesPagingSource
import kotlinx.coroutines.flow.Flow

class UpcomingMoviesViewModel(private val repository: MoviesRepository) : ViewModel() {
    fun getUpcomingMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                UpcomingMoviesPagingSource(repository = repository)
            }
        ).flow.cachedIn(viewModelScope)
    }
}