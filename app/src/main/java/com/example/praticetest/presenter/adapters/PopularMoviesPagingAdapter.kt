package com.example.praticetest.presenter.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.praticetest.data.vo.Movie
import com.example.praticetest.databinding.ItemListMoviesBinding
import com.example.praticetest.domain.constants.Constants.Companion.BASE_URL_IMAGE


class PopularMoviesPagingAdapter(var onClick: (movie: Movie) -> Unit = {}) :
    PagingDataAdapter<Movie, PopularMoviesPagingAdapter.ViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemListMoviesBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(val binding: ItemListMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            Glide.with(itemView)
                .load(BASE_URL_IMAGE + movie.posterPath)
                .transform(CenterCrop())
                .into(binding.coverMovieImageView)

            binding.movieTitleTextView.text = movie.title

            binding.coverMovieImageView.setOnClickListener {
                onClick.invoke(movie)
            }

            binding.movieTitleTextView.text = movie.title
            binding.releaseDateTextView.visibility = View.GONE
            binding.movieRateTextView.visibility = View.GONE

        }

    }
}

