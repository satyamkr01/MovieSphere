package com.unifydream.moviesphere.data.repository

import com.unifydream.moviesphere.data.remote.MovieApiService
import com.unifydream.moviesphere.data.remote.response.MovieDetails
import com.unifydream.moviesphere.data.remote.response.MovieResponse
import com.unifydream.moviesphere.data.remote.response.CastResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDetailsRepository @Inject constructor(private val movieApiService: MovieApiService) {

    fun getMoviesDetailsRepo(movieId: String): Flow<MovieDetails> = flow {
        val response = movieApiService.getMoviesDetails(movieId.toInt())
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getCastMoviesRepo(movieId: String): Flow<CastResponse> = flow {
        val response = movieApiService.getMovieCast(movieId.toInt())
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getSimilarMoviesRepo(movieId: String): Flow<MovieResponse> = flow {
        val response = movieApiService.getSimilarMovies(movieId.toInt())
        emit(response)
    }.flowOn(Dispatchers.IO)
}