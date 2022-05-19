package com.example.praticetest.di


import com.example.praticetest.data.api.MovieService
import com.example.praticetest.data.repository.MoviesRepository
import com.example.praticetest.domain.constants.Constants.Companion.API_KEY
import com.example.praticetest.domain.constants.Constants.Companion.BASE_URL
import com.example.praticetest.presenter.viewmodel.PopularMoviesViewModel
import com.example.praticetest.presenter.viewmodel.SearchMoviesViewModel
import com.example.praticetest.presenter.viewmodel.TopRatedMoviesViewModel
import com.example.praticetest.presenter.viewmodel.UpcomingMoviesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {


    single<MoviesRepository> { MoviesRepository(get()) }
    viewModel<PopularMoviesViewModel> { PopularMoviesViewModel(get()) }
    viewModel<UpcomingMoviesViewModel> { UpcomingMoviesViewModel(get()) }
    viewModel<TopRatedMoviesViewModel> { TopRatedMoviesViewModel(get()) }
    viewModel<SearchMoviesViewModel> { SearchMoviesViewModel(get()) }

    single {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)
        okHttpClient.addInterceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url

            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .addQueryParameter("language", "pt-BR")
                .build()

            chain.proceed(original.newBuilder().url(url).build())
        }

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    single { get<Retrofit>().create(MovieService::class.java) }
}