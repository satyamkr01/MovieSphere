package com.unifydream.moviesphere.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = Companion.REPLACE)
    suspend fun insertMovieInList(favoriteMovie: FavoriteMovie)

    @Query("DELETE FROM watch_list_table WHERE mediaId =:mediaId")
    suspend fun removeFromList(mediaId: Int)

    @Query("DELETE FROM watch_list_table")
    suspend fun deleteList()

    @Query("SELECT EXISTS (SELECT 1 FROM watch_list_table WHERE mediaId = :mediaId)")
    suspend fun exists(mediaId: Int): Int

    @Query("SELECT * FROM watch_list_table ORDER BY addedOn DESC")
    fun getAllFavoriteMovie(): Flow<List<FavoriteMovie>>
}

