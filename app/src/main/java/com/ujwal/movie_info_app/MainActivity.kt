package com.ujwal.movie_info_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ujwal.movie_info_app.details.DetailsScreen
import com.ujwal.movie_info_app.movieList.presentation.MovieListViewModel
import com.ujwal.movie_info_app.movieList.presentation.PopularMovieScreen
import com.ujwal.movie_info_app.movieList.util.Screen
import com.ujwal.movie_info_app.ui.theme.Movie_Info_AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Movie_Info_AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val movieListViewModel = hiltViewModel<MovieListViewModel>()
                    val movieListState = movieListViewModel.movieListState.collectAsState().value
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.PopularMovieList.route
                    ) {
                        composable(Screen.PopularMovieList.route) {
                            PopularMovieScreen(
                                movieListState = movieListState,
                                navHostController = navController,
                                onEvent = movieListViewModel::onEvent
                            )
                        }

                        composable(
                            Screen.DetailsScreen.route + "/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") { type = NavType.IntType }
                            )
                        ) {
                            DetailsScreen()
                        }
                    }
                }
            }
        }
    }
}