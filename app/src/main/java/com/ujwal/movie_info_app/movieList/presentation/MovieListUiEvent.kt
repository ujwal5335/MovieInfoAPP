package com.ujwal.movie_info_app.movieList.presentation

sealed interface MovieListUiEvent {
    data class Paginate(val movie: String) : MovieListUiEvent
    data object Navigate: MovieListUiEvent
}