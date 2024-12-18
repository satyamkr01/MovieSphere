package com.unifydream.moviesphere.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.unifydream.moviesphere.MainDispatcherRule
import com.unifydream.moviesphere.data.models.Movies
import com.unifydream.moviesphere.data.remote.response.MovieResponse
import com.unifydream.moviesphere.data.repository.HomeRepository
import com.unifydream.moviesphere.utlis.MovieState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: HomeViewModel
    private val repository: HomeRepository = mock()

    @Before
    fun setup() {
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `fetchDiscoverMovies emits success state when repository returns data`() = runTest {
        val mockResponse = MovieResponse(
            page = 1,
            results = listOf(
                Movies(
                    adult = false,
                    backdropPath = null,
                    posterPath = null,
                    genreIds = listOf(28, 12),
                    genres = null,
                    mediaType = null,
                    id = 1,
                    imdbId = null,
                    originalLanguage = "en",
                    overview = "Test overview",
                    popularity = 10.0,
                    releaseDate = "2023-01-01",
                    runtime = null,
                    title = "Test Movie",
                    video = false,
                    voteAverage = 8.0,
                    voteCount = 100
                )
            ),
            totalPages = 1,
            totalResults = 1
        )
        whenever(repository.getDiscoverMoviesRepo()).thenReturn(flowOf(mockResponse))

        viewModel.fetchDiscoverMovies()

        val state = viewModel.discoveryMovieResponses.first()
        assert(state is MovieState.Success && state.data == mockResponse)
    }

    @Test
    fun `fetchDiscoverMovies emits error state when repository throws exception`() = runTest {
        whenever(repository.getDiscoverMoviesRepo()).thenReturn(flow { throw Exception("Network error") })

        viewModel.fetchDiscoverMovies()

        val state = viewModel.discoveryMovieResponses.first()
        assert(state is MovieState.Error && state.message == "An error occurred. Please try again.")
    }

    @Test
    fun `fetchTrendingMovies emits success state when repository returns data`() = runTest {
        val mockResponse = MovieResponse(
            page = 1,
            results = listOf(
                Movies(
                    adult = false,
                    backdropPath = null,
                    posterPath = null,
                    genreIds = listOf(28, 12),
                    genres = null,
                    mediaType = null,
                    id = 1,
                    imdbId = null,
                    originalLanguage = "en",
                    overview = "Test overview",
                    popularity = 10.0,
                    releaseDate = "2023-01-01",
                    runtime = null,
                    title = "Test Movie",
                    video = false,
                    voteAverage = 8.0,
                    voteCount = 100
                )
            ),
            totalPages = 1,
            totalResults = 1
        )
        whenever(repository.getTrendingMoviesRepo()).thenReturn(flowOf(mockResponse))

        viewModel.fetchTrendingMovies()

        val state = viewModel.trendingMovieResponses.first()
        assert(state is MovieState.Success && state.data == mockResponse)
    }

    @Test
    fun `fetchTrendingMovies emits error state when repository throws exception`() = runTest {
        whenever(repository.getTrendingMoviesRepo()).thenReturn(flow { throw Exception("Network error") })

        viewModel.fetchTrendingMovies()

        val state = viewModel.trendingMovieResponses.first()
        assert(state is MovieState.Error && state.message == "An error occurred. Please try again.")
    }
}