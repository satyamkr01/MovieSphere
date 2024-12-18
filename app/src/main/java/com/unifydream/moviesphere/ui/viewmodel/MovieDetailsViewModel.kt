package com.unifydream.moviesphere.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifydream.moviesphere.data.remote.response.MovieDetails
import com.unifydream.moviesphere.data.remote.response.MovieResponse
import com.unifydream.moviesphere.data.repository.MovieDetailsRepository
import com.unifydream.moviesphere.data.models.Cast
import com.unifydream.moviesphere.utlis.MovieState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: MovieDetailsRepository) :
    ViewModel() {
    private val _response: MutableStateFlow<MovieState<MovieDetails?>> =
        MutableStateFlow(MovieState.Loading)
    val detailsMovieResponses: StateFlow<MovieState<MovieDetails?>> = _response

    private val _response2: MutableStateFlow<MovieState<MovieResponse?>> =
        MutableStateFlow(MovieState.Loading)
    val similarMovieResponses: StateFlow<MovieState<MovieResponse?>> = _response2

    private val _response3: MutableStateFlow<MovieState<List<Cast>?>> =
        MutableStateFlow(MovieState.Loading)
    val castMovieResponses: StateFlow<MovieState<List<Cast>?>> = _response3

    fun fetchMoviesDetails(movieId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMoviesDetailsRepo(movieId).first()
                _response.emit(MovieState.Success(response))
            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response.emit(MovieState.Error(errorMessage))
            }
        }
    }

    fun fetchSimilarMovies(movieId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSimilarMoviesRepo(movieId).first()
                _response2.emit(MovieState.Success(response))
            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response2.emit(MovieState.Error(errorMessage))
            }
        }
    }

    fun fetchCasteOfMovies(movieId: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCastMoviesRepo(movieId).first()

                val castList = response.castResult
                _response3.emit(MovieState.Success(castList))

            } catch (e: Exception) {
                val errorMessage = "An error occurred. Please try again."
                _response3.emit(MovieState.Error(errorMessage))
            }
        }
    }
}

