package com.unifydream.moviesphere.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unifydream.moviesphere.data.local.FavoriteMovie
import com.unifydream.moviesphere.data.repository.FavoriteMovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(private val favoriteMovieRepository: FavoriteMovieRepository) :
    ViewModel() {
    private var _exist = mutableIntStateOf(0)
    val exist: State<Int> = _exist

    private val _myMovieData = mutableStateOf<Flow<List<FavoriteMovie>>>(emptyFlow())
    val myMovieData: State<Flow<List<FavoriteMovie>>> = _myMovieData

    init {
        allMovieData()
    }

    private fun allMovieData() {
       _myMovieData.value = favoriteMovieRepository.getAllData()
    }

    fun exist(mediaId: Int) {
        viewModelScope.launch {
            _exist.value = favoriteMovieRepository.exist(mediaId = mediaId)
        }
    }

    fun addToWatchList(movie: FavoriteMovie) {
        viewModelScope.launch {
            favoriteMovieRepository.insertMovieInList(movie)
        }.invokeOnCompletion {
            exist(movie.mediaId)
        }
    }

    fun removeFromWatchList(mediaId: Int) {
        viewModelScope.launch {
            favoriteMovieRepository.removeFromList(mediaId = mediaId)
        }.invokeOnCompletion {
            exist(mediaId = mediaId)
        }
    }
}