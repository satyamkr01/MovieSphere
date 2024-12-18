package com.unifydream.moviesphere.ui.viewmodel

import com.unifydream.moviesphere.MainDispatcherRule
import com.unifydream.moviesphere.data.local.FavoriteMovie
import com.unifydream.moviesphere.data.repository.FavoriteMovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class FavoriteMovieViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: FavoriteMovieViewModel
    private val favoriteMovieRepository: FavoriteMovieRepository = mock()

    @Before
    fun setup() {
        viewModel = FavoriteMovieViewModel(favoriteMovieRepository)
    }

    @Test
    fun `allMovieData should fetch all favorite movies`() = runTest {
        val mockMovies = listOf(
            FavoriteMovie(
                mediaId = 1,
                imagePath = "path1",
                title = "Movie 1",
                releaseDate = "2023-01-01",
                rating = 8.5,
                addedOn = "2023-12-01"
            ),
            FavoriteMovie(
                mediaId = 2,
                imagePath = "path2",
                title = "Movie 2",
                releaseDate = "2023-01-02",
                rating = 7.5,
                addedOn = "2023-12-02"
            )
        )
        whenever(favoriteMovieRepository.getAllData()).thenReturn(flowOf(mockMovies))

        val result = viewModel.myMovieData.value.first()

        assert(result == mockMovies)
    }

    @Test
    fun `exist should update exist state`() = runTest {
        val mediaId = 1
        whenever(favoriteMovieRepository.exist(mediaId)).thenReturn(1)

        viewModel.exist(mediaId)
        advanceUntilIdle()

        assert(viewModel.exist.value == 1)
    }

    @Test
    fun `addToWatchList should insert movie and update exist state`() = runTest {
        val mockMovie = FavoriteMovie(
            mediaId = 1,
            imagePath = "path1",
            title = "Movie 1",
            releaseDate = "2023-01-01",
            rating = 8.5,
            addedOn = "2023-12-01"
        )
        whenever(favoriteMovieRepository.insertMovieInList(mockMovie)).thenAnswer { }
        whenever(favoriteMovieRepository.exist(mockMovie.mediaId)).thenReturn(1)

        viewModel.addToWatchList(mockMovie)
        advanceUntilIdle()

        verify(favoriteMovieRepository).insertMovieInList(mockMovie)
        assert(viewModel.exist.value == 1)
    }

    @Test
    fun `removeFromWatchList should remove movie and update exist state`() = runTest {
        val mediaId = 1
        whenever(favoriteMovieRepository.removeFromList(mediaId)).thenAnswer { }
        whenever(favoriteMovieRepository.exist(mediaId)).thenReturn(0)

        viewModel.removeFromWatchList(mediaId)
        advanceUntilIdle()

        verify(favoriteMovieRepository).removeFromList(mediaId)
        assert(viewModel.exist.value == 0)
    }
}