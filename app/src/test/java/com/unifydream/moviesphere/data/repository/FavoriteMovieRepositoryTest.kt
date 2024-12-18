package com.unifydream.moviesphere.data.repository

import com.unifydream.moviesphere.data.local.FavoriteMovie
import com.unifydream.moviesphere.data.local.MovieDao
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.verify

class FavoriteMovieRepositoryTest {

    private val mockMovieDao = mock(MovieDao::class.java)
    private val repository = FavoriteMovieRepository(mockMovieDao)

    @Test
    fun `insertMovieInList should call MovieDao's insertMovieInList`() = runTest {
        val favoriteMovie = FavoriteMovie(
            mediaId = 1,
            imagePath = "/path/to/image",
            title = "Test Movie",
            releaseDate = "2024-12-01",
            rating = 8.5,
            addedOn = "2024-12-01"
        )

        repository.insertMovieInList(favoriteMovie)

        verify(mockMovieDao).insertMovieInList(favoriteMovie)
    }

    @Test
    fun `removeFromList should call MovieDao's removeFromList`() = runTest {
        val mediaId = 1

        repository.removeFromList(mediaId)

        verify(mockMovieDao).removeFromList(mediaId)
    }

    @Test
    fun `exist should return correct value from MovieDao`() = runTest {
        val mediaId = 1
        `when`(mockMovieDao.exists(mediaId)).thenReturn(1)

        val result = repository.exist(mediaId)

        assertEquals(1, result)
        verify(mockMovieDao).exists(mediaId)
    }

    @Test
    fun `getAllData should return correct flow from MovieDao`() = runTest {
        val favoriteMovies = listOf(
            FavoriteMovie(
                mediaId = 1,
                imagePath = "/path/to/image1",
                title = "Test Movie 1",
                releaseDate = "2024-12-01",
                rating = 8.0,
                addedOn = "2024-12-01"
            ),
            FavoriteMovie(
                mediaId = 2,
                imagePath = "/path/to/image2",
                title = "Test Movie 2",
                releaseDate = "2024-12-02",
                rating = 7.5,
                addedOn = "2024-12-02"
            )
        )
        `when`(mockMovieDao.getAllFavoriteMovie()).thenReturn(flowOf(favoriteMovies))

        val result = repository.getAllData()

        result.collect { data ->
            assertEquals(favoriteMovies, data)
        }
        verify(mockMovieDao).getAllFavoriteMovie()
    }
}