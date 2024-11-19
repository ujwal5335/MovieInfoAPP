package com.ujwal.movie_info_app.movieList.data.repository

import com.ujwal.movie_info_app.movieList.data.local.MovieDatabase
import com.ujwal.movie_info_app.movieList.data.mapper.toMovie
import com.ujwal.movie_info_app.movieList.data.mapper.toMovieEntity
import com.ujwal.movie_info_app.movieList.data.remote.MovieApi
import com.ujwal.movie_info_app.movieList.domain.model.Movie
import com.ujwal.movie_info_app.movieList.domain.repository.MovieListRepository
import com.ujwal.movie_info_app.movieList.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {
    override suspend fun getMoviesList(
        fetchFromRemote: Boolean,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMovieList = movieDatabase.movieDao.getMovieList()

            val loadLocalMovie = localMovieList.isNotEmpty() && !fetchFromRemote

            if (loadLocalMovie) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie()
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val movieListFromApi = try {
                movieApi.getMoviesList(page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val movieEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                    movieDto.toMovieEntity()
                }
            }

            movieDatabase.movieDao.upsertMovieList(movieEntities)

            emit(Resource.Success(
                movieEntities.map { it.toMovie() }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMoviesByID(id)

            if (movieEntity != null) {
                emit(
                    Resource.Success(movieEntity.toMovie())
                )
                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error: No such movie"))

            emit(Resource.Loading(false))
        }
    }
}