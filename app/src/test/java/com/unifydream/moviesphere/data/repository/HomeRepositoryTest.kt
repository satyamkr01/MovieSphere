package com.unifydream.moviesphere.data.repository

import com.unifydream.moviesphere.data.remote.MovieApiService
import com.unifydream.moviesphere.data.remote.response.GenreResponse
import com.unifydream.moviesphere.data.remote.response.MovieResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class HomeRepositoryTest {

    private val movieApiService: MovieApiService = mock()
    private val repository = HomeRepository(movieApiService)

    @Test
    fun `getNowPlayingMoviesRepo returns expected response`() = runTest {
        val expectedResponse = MovieResponse(1, emptyList(), 1, 0)
        `when`(movieApiService.getNowPlayingMovies(2)).thenReturn(expectedResponse)

        val response = repository.getNowPlayingMoviesRepo().first()

        assertEquals(expectedResponse, response)
        verify(movieApiService).getNowPlayingMovies(2)
    }

    @Test
    fun `getPopularMoviesRepo returns expected response`() = runTest {
        val expectedResponse = MovieResponse(1, emptyList(), 1, 0)
        `when`(movieApiService.getPopularMovies(1)).thenReturn(expectedResponse)

        val response = repository.getPopularMoviesRepo().first()

        assertEquals(expectedResponse, response)
        verify(movieApiService).getPopularMovies(1)
    }

    @Test
    fun `getDiscoverMoviesRepo returns expected response`() = runTest {
        val expectedResponse = MovieResponse(1, emptyList(), 1, 0)
        `when`(movieApiService.getDiscoverMovies(1)).thenReturn(expectedResponse)

        val response = repository.getDiscoverMoviesRepo().first()

        assertEquals(expectedResponse, response)
        verify(movieApiService).getDiscoverMovies(1)
    }

    @Test
    fun `getTrendingMoviesRepo returns expected response`() = runTest {
        val expectedResponse = MovieResponse(1, emptyList(), 1, 0)
        `when`(movieApiService.getTrendingMovies(1)).thenReturn(expectedResponse)

        val response = repository.getTrendingMoviesRepo().first()

        assertEquals(expectedResponse, response)
        verify(movieApiService).getTrendingMovies(1)
    }

    @Test
    fun `getUpcomingMoviesRepo returns expected response`() = runTest {
        val expectedResponse = MovieResponse(1, emptyList(), 1, 0)
        `when`(movieApiService.getUpcomingMovies(1)).thenReturn(expectedResponse)

        val response = repository.getUpcomingMoviesRepo().first()

        assertEquals(expectedResponse, response)
        verify(movieApiService).getUpcomingMovies(1)
    }

    @Test
    fun `getMovieGenresRepo returns expected response`() = runTest {
        val expectedResponse = GenreResponse(emptyList())
        `when`(movieApiService.getMovieGenres()).thenReturn(expectedResponse)

        val response = repository.getMovieGenresRepo().first()

        assertEquals(expectedResponse, response)
        verify(movieApiService).getMovieGenres()
    }
}