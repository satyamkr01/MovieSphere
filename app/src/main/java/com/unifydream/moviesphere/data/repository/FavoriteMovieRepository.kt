package com.unifydream.moviesphere.data.repository

import com.unifydream.moviesphere.data.local.MovieDao
import com.unifydream.moviesphere.data.local.FavoriteMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteMovieRepository @Inject constructor(private val movieDao: MovieDao) {
    suspend fun insertMovieInList(myListMovie: FavoriteMovie) {
        movieDao.insertMovieInList( myListMovie)
    }

    suspend fun removeFromList(mediaId: Int) {
        movieDao.removeFromList(mediaId)
    }

    suspend fun exist(mediaId: Int): Int {
        return movieDao.exists(mediaId)
    }

    fun getAllData(): Flow<List<FavoriteMovie>> {
        return movieDao.getAllFavoriteMovie()
    }
}