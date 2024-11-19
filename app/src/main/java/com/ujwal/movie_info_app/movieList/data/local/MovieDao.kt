package com.ujwal.movie_info_app.movieList.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertMovieList(movieList: List<MovieEntity>)

    @Query("SELECT * FROM MovieEntity WHERE id = :id")
    suspend fun getMoviesByID(id: Int): MovieEntity

    @Query("SELECT * FROM MovieEntity")
    suspend fun getMovieList(): List<MovieEntity>
}