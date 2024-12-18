package com.unifydream.moviesphere.ui.view.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.unifydream.moviesphere.ui.viewmodel.FavoriteMovieViewModel
import com.unifydream.moviesphere.graph.MovieAppScreen
import com.unifydream.moviesphere.ui.view.screens.components.DismissBackground
import com.unifydream.moviesphere.ui.view.screens.components.SearchMovieCard
import com.unifydream.moviesphere.utlis.CenteredTopBar
import com.unifydream.moviesphere.utlis.Constants.Companion.BASE_POSTER_IMAGE_URL

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FavoriteMovieScreen(
    favoriteMovieViewModel: FavoriteMovieViewModel = hiltViewModel(),
    navController: NavHostController) {
    val roomData = favoriteMovieViewModel.myMovieData.value.collectAsState(initial = emptyList()).value
    Scaffold(topBar = {
        CenteredTopBar("Favorite Movie (${roomData.size})",
            { navController.navigate(MovieAppScreen.MOVIE_SEARCH.route) },
            { navController.navigate(MovieAppScreen.MOVIE_HOME.route) })
    }) { padding: PaddingValues ->
        if (roomData.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "No Data in Favorite List",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        } else {
            LazyColumn(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                items(roomData, key = { it.mediaId }) { it ->
                    val movie = it
                    val imageUrl = movie.imagePath?.let { BASE_POSTER_IMAGE_URL + it }
                    val context = LocalContext.current
                    val dismissState = rememberSwipeToDismissBoxState(
                        confirmValueChange = {
                            when (it) {
                                SwipeToDismissBoxValue.EndToStart -> {
                                    favoriteMovieViewModel.removeFromWatchList(movie.mediaId)
                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT)
                                        .show()
                                }

                                SwipeToDismissBoxValue.Settled -> {
                                    return@rememberSwipeToDismissBoxState false
                                }

                                SwipeToDismissBoxValue.StartToEnd -> {

                                }
                            }
                            return@rememberSwipeToDismissBoxState true
                        },
                        positionalThreshold = { it * .25f }
                    )

                    SwipeToDismissBox(
                        state = dismissState,
                        enableDismissFromStartToEnd = false,
                        backgroundContent = { DismissBackground(dismissState) },
                        content = {
                            SearchMovieCard(
                                imageUrl = imageUrl ?: "",
                                title = movie.title,
                                overview = movie.releaseDate
                            ) {
                                navController.navigate(MovieAppScreen.MOVIE_HOME_DETAILS.route + "/${movie.mediaId}")
                            }
                        })
                }
            }
        }
    }
}