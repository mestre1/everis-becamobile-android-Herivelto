package com.example.praticetest.presenter.viewmodel

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.praticetest.data.repository.MoviesRepository
import com.example.praticetest.data.vo.Movie
import com.example.praticetest.domain.constants.Constants
import com.example.praticetest.presenter.pagingsource.TopRatedMoviesPagingSource
import kotlinx.coroutines.flow.Flow

class TopRatedMoviesViewModel(private val repository: MoviesRepository) : ViewModel() {
    fun getTopRatedMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                TopRatedMoviesPagingSource(repository = repository)
            }
        ).flow
    }
}