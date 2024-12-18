package com.unifydream.moviesphere.data.repository

import com.unifydream.moviesphere.data.models.Cast
import com.unifydream.moviesphere.data.models.Genre
import com.unifydream.moviesphere.data.models.Movies
import com.unifydream.moviesphere.data.remote.MovieApiService
import com.unifydream.moviesphere.data.remote.response.CastResponse
import com.unifydream.moviesphere.data.remote.response.MovieDetails
import com.unifydream.moviesphere.data.remote.response.MovieResponse
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class MovieDetailsRepositoryTest {

    private val mockMovieApiService = mock(MovieApiService::class.java)
    private val repository = MovieDetailsRepository(mockMovieApiService)

    @Test
    fun `getMoviesDetailsRepo should return MovieDetails`() = runTest {
        val movieId = "123"
        val mockResponse = MovieDetails(
            adult = false,
            backdropPath = "/path/to/backdrop",
            belongsToCollection = null,
            budget = 1000000,
            genres = emptyList(),
            homepage = "https://example.com",
            id = 123,
            imdbId = "tt1234567",
            originalLanguage = "en",
            originalTitle = "Test Movie",
            overview = "A test movie description",
            popularity = 7.5,
            posterPath = "/path/to/poster",
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            releaseDate = "2024-12-01",
            revenue = 5000000,
            runtime = 120,
            spokenLanguages = emptyList(),
            status = "Released",
            tagline = "Test tagline",
            title = "Test Movie",
            video = false,
            voteAverage = 8.0,
            voteCount = 200
        )
        `when`(mockMovieApiService.getMoviesDetails(movieId.toInt())).thenReturn(mockResponse)

        val flow = repository.getMoviesDetailsRepo(movieId)

        flow.collect { result ->
            assertEquals(mockResponse, result)
        }
        verify(mockMovieApiService).getMoviesDetails(movieId.toInt())
    }

    @Test
    fun `getCastMoviesRepo should return CastResponse`() = runTest {
        val movieId = "123"
        val mockResponse = CastResponse(
            id = 123,
            castResult = listOf(
                Cast(department = "Acting", name = "Actor 1", profilePath = "/path/to/profile1"),
                Cast(department = "Directing", name = "Actor 2", profilePath = "/path/to/profile2")
            )
        )
        `when`(mockMovieApiService.getMovieCast(movieId.toInt())).thenReturn(mockResponse)

        val flow = repository.getCastMoviesRepo(movieId)

        flow.collect { result ->
            assertEquals(mockResponse, result)
        }
        verify(mockMovieApiService).getMovieCast(movieId.toInt())
    }

    @Test
    fun `getSimilarMoviesRepo should return MovieResponse`() = runTest {
        val movieId = "123"
        val mockResponse = MovieResponse(
            page = 1,
            results = listOf(
                Movies(
                    id = 101,
                    title = "Similar Movie 1",
                    overview = "Overview 1",
                    posterPath = "/path/to/poster1",
                    adult = false,
                    backdropPath = "/path/to/backdrop1",
                    genreIds = listOf(1, 2),
                    genres = listOf(Genre(id = 1, name = "Action"), Genre(id = 2, name = "Drama")),
                    mediaType = "movie",
                    imdbId = "tt1234567",
                    originalLanguage = "en",
                    popularity = 50.0,
                    releaseDate = "2024-12-01",
                    runtime = 120,
                    video = false,
                    voteAverage = 7.8,
                    voteCount = 200
                ),
                Movies(
                    id = 102,
                    title = "Similar Movie 2",
                    overview = "Overview 2",
                    posterPath = "/path/to/poster2",
                    adult = false,
                    backdropPath = "/path/to/backdrop2",
                    genreIds = listOf(2, 3),
                    genres = listOf(Genre(id = 2, name = "Drama"), Genre(id = 3, name = "Comedy")),
                    mediaType = "movie",
                    imdbId = "tt2345678",
                    originalLanguage = "en",
                    popularity = 60.0,
                    releaseDate = "2024-11-15",
                    runtime = 110,
                    video = false,
                    voteAverage = 6.5,
                    voteCount = 150
                )
            ),
            totalPages = 1,
            totalResults = 2
        )
        `when`(mockMovieApiService.getSimilarMovies(movieId.toInt())).thenReturn(mockResponse)

        val flow = repository.getSimilarMoviesRepo(movieId)

        flow.collect { result ->
            assertEquals(mockResponse, result)
        }
        verify(mockMovieApiService).getSimilarMovies(movieId.toInt())
    }
}