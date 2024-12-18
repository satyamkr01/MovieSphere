package com.unifydream.moviesphere.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.unifydream.moviesphere.data.remote.response.MovieDetails
import com.unifydream.moviesphere.data.repository.MovieDetailsRepository
import com.unifydream.moviesphere.utlis.MovieState
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class MovieDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MovieDetailsViewModel

    @Mock
    private lateinit var repository: MovieDetailsRepository

    @Before
    fun setUp() {
        repository = mock()
        viewModel = MovieDetailsViewModel(repository)
    }

    @Test
    fun `fetchMoviesDetails emits success state`() = runTest {
        val movieDetails = MovieDetails(
            adult = false,
            backdropPath = "path",
            belongsToCollection = null,
            budget = 1000000,
            genres = emptyList(),
            homepage = "https://example.com",
            id = 123,
            imdbId = "tt1234567",
            originalLanguage = "en",
            originalTitle = "Test Movie",
            overview = "Test overview",
            popularity = 10.0,
            posterPath = "posterPath",
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            releaseDate = "2023-01-01",
            revenue = 2000000,
            runtime = 120,
            spokenLanguages = emptyList(),
            status = "Released",
            tagline = "Test tagline",
            title = "Test Movie",
            video = false,
            voteAverage = 8.0,
            voteCount = 100
        )

        whenever(repository.getMoviesDetailsRepo(any())).thenReturn(flow { emit(movieDetails) })

        viewModel.fetchMoviesDetails("123")

        viewModel.detailsMovieResponses.test {
            assert(awaitItem() is MovieState.Loading)
            assert(awaitItem() is MovieState.Success<*>)
            cancelAndConsumeRemainingEvents()
        }

        verify(repository).getMoviesDetailsRepo("123")
    }

    @Test
    fun `fetchMoviesDetails emits error state on exception`() = runTest {
        whenever(repository.getMoviesDetailsRepo(any())).thenReturn(flow { throw Exception("Network error") })

        viewModel.fetchMoviesDetails("123")

        viewModel.detailsMovieResponses.test {
            assert(awaitItem() is MovieState.Loading)
            val errorState = awaitItem() as MovieState.Error
            assert(errorState.message == "An error occurred. Please try again.")
            cancelAndConsumeRemainingEvents()
        }

        verify(repository).getMoviesDetailsRepo("123")
    }
}