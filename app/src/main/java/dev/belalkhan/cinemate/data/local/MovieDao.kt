package dev.belalkhan.cinemate.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertAll(movies: List<LocalMovie>)

    @Query("SELECT * FROM movies ORDER BY localId ASC")
    fun getMovies(): PagingSource<Int, LocalMovie>

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
