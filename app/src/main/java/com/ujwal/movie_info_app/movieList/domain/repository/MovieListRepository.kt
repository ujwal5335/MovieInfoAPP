package com.ujwal.movie_info_app.movieList.domain.repository

import com.ujwal.movie_info_app.movieList.domain.model.Movie
import com.ujwal.movie_info_app.movieList.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMoviesList(
        fetchFromRemote: Boolean,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}