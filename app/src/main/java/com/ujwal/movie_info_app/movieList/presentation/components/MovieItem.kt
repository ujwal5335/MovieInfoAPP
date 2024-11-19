package com.ujwal.movie_info_app.movieList.presentation.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.ujwal.movie_info_app.R
import com.ujwal.movie_info_app.movieList.data.remote.MovieApi
import com.ujwal.movie_info_app.movieList.domain.model.Movie
import com.ujwal.movie_info_app.movieList.util.RatingBar
import com.ujwal.movie_info_app.movieList.util.Screen

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun MovieItem(
    movie: Movie,
    navHostController: NavHostController
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + movie.backdropPath)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    val dominantColor by remember {
        mutableStateOf(defaultColor)
    }

    val checkedState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        dominantColor
                    )
                )
            )
            .clickable {
                navHostController.navigate(Screen.DetailsScreen.route + "/${movie.id}")
            }
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_image_not_supported_24),
                    contentDescription = movie.title
                )
            }
        }

        if (imageState is AsyncImagePainter.State.Success) {
            //dominantColor = get
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp)),
                painter = imageState.painter,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            text = movie.title,
            color = Color.White,
            fontSize = 15.sp,
            maxLines = 1
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
        ) {
            RatingBar(
                starsModifier = Modifier.size(18.dp),
                rating = movie.voteAverage / 2
            )

            Text(
                modifier = Modifier.padding(start = 4.dp),
                text = movie.voteAverage.toString().take(3),
                color = Color.LightGray,
                fontSize = 15.sp,
                maxLines = 1
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 5.dp),
        ) {
            Text(
                modifier = Modifier.padding(start = 15.dp),
                text = movie.releaseDate,
                color = Color.White,
                fontSize = 15.sp,
            )

            IconToggleButton(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = !checkedState.value
                },

                modifier = Modifier.fillMaxWidth()
            ) {

                val transition = updateTransition(checkedState.value, label = "clicked")

                val tint by transition.animateColor(label = "iconColor") { isChecked ->
                    if (isChecked) Color.Red else Color.Black
                }

                val size by transition.animateDp(
                    transitionSpec = {
                        if (false isTransitioningTo true) {

                            keyframes {

                                durationMillis = 250

                                30.dp at 0 with LinearOutSlowInEasing // for 0-15 ms
                                35.dp at 15 with FastOutLinearInEasing // for 15-75 ms
                                40.dp at 75 // ms
                                35.dp at 150 // ms
                            }
                        } else {
                            spring(stiffness = Spring.StiffnessVeryLow)
                        }
                    },
                    label = "Size"
                ) { 30.dp }

                Icon(
                    imageVector = if (checkedState.value) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = "Icon",
                    tint = tint,
                    modifier = Modifier.size(size)
                )
            }
        }
    }
}