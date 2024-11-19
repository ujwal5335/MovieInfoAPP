package com.ujwal.movie_info_app.movieList.data.mapper

import com.ujwal.movie_info_app.movieList.data.local.MovieEntity
import com.ujwal.movie_info_app.movieList.data.remote.response.MovieDto
import com.ujwal.movie_info_app.movieList.domain.model.Movie

fun MovieDto.toMovieEntity(): MovieEntity {
    return MovieEntity(
        backdropPath = backdropPath ?: "",
        id = id ?: -1,
        originalLanguage = originalLanguage ?: "",
        overview = overview ?: "",
        posterPath = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: "",
        voteAverage = voteAverage ?: 0.0
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        backdropPath = backdropPath,
        id = id,
        originalLanguage = originalLanguage,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage
    )
}