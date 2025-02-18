package com.example.praticetest.presenter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.praticetest.data.vo.Movie
import com.example.praticetest.databinding.ItemSearchMoviesBinding
import com.example.praticetest.domain.constants.Constants.Companion.BASE_URL_IMAGE
import com.example.praticetest.domain.utils.dateFormatter


class SearchMoviesPagingAdapter(var onClick: (movie: Movie) -> Unit = {}) :
    PagingDataAdapter<Movie, SearchMoviesPagingAdapter.SearchMovieViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchMovieViewHolder {
        return SearchMovieViewHolder(
            ItemSearchMoviesBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }


    override fun onBindViewHolder(holder: SearchMovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }


    inner class SearchMovieViewHolder(val binding: ItemSearchMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load(BASE_URL_IMAGE + movie.posterPath)
                .transform(CenterCrop())
                .into(binding.itemSearchCoverImageView)

            binding.itemSearchCoverImageView.setOnClickListener {
                onClick.invoke(movie)
            }

            binding.movieSearchTitleTextView.text = movie.title
            binding.movieSearchYearTextView.text = movie.releaseDate.dateFormatter()
            binding.movieSearchRatingTextView.text = movie.rating.toString()
        }
    }
}


