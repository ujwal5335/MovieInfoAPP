package com.ujwal.movie_info_app.movieList.util

sealed class Screen(val route: String) {
    data object PopularMovieList: Screen("popularMovie")
    data object DetailsScreen: Screen("detailsScreen")
}