package com.unifydream.moviesphere.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.unifydream.moviesphere.data.paging.MovieGenrePagingSource
import com.unifydream.moviesphere.data.paging.MoviePagingSource
import com.unifydream.moviesphere.data.remote.MovieApiService
import com.unifydream.moviesphere.data.remote.response.MovieResponse
import com.unifydream.moviesphere.data.models.Movies
import com.unifydream.moviesphere.data.remote.response.GenreResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class HomeRepository @Inject constructor(private val movieApiService: MovieApiService) {

    fun getNowPlayingMoviesRepo(): Flow<MovieResponse> = flow {
        val response = movieApiService.getNowPlayingMovies(2)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getPopularMoviesRepo(): Flow<MovieResponse> = flow {
        val response = movieApiService.getPopularMovies(1)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getAllMoviesPagination(tags: String): Pager<Int, Movies> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieApiService,tags) }
        )
    }

    fun getGenresWiseMovieRepo(tags: Int): Pager<Int, Movies> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MovieGenrePagingSource(movieApiService,tags) }
        )
    }

    fun getDiscoverMoviesRepo(): Flow<MovieResponse> = flow {
        val response = movieApiService.getDiscoverMovies(1)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getTrendingMoviesRepo(): Flow<MovieResponse> = flow {
        val response = movieApiService.getTrendingMovies(1)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getUpcomingMoviesRepo(): Flow<MovieResponse> = flow {
        val response = movieApiService.getUpcomingMovies(1)
        emit(response)
    }.flowOn(Dispatchers.IO)

    fun getMovieGenresRepo(): Flow<GenreResponse> = flow {
        val response = movieApiService.getMovieGenres()
        emit(response)
    }.flowOn(Dispatchers.IO)
}
