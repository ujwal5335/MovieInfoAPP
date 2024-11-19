package com.ujwal.movie_info_app.movieList.presentation

import com.ujwal.movie_info_app.movieList.domain.model.Movie

data class MovieListState (
    val isLoading: Boolean = false,

    val popularMovieListPage: Int = 1,

    val popularMovieList: List<Movie> = emptyList()
)