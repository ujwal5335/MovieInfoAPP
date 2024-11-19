package com.ujwal.movie_info_app.details

import com.ujwal.movie_info_app.movieList.domain.model.Movie

data class DetailsState (
    val isLoading: Boolean = false,
    val movie: Movie? =null
)