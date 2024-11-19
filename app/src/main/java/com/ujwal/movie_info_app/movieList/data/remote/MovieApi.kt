package com.ujwal.movie_info_app.movieList.data.remote

import com.ujwal.movie_info_app.BuildConfig
import com.ujwal.movie_info_app.movieList.data.remote.response.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getMoviesList(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieListDto

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}