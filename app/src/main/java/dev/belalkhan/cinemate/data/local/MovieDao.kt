package dev.belalkhan.cinemate.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertAll(planets: List<LocalMovie>)

    @Query("SELECT * FROM movies")
    fun pagingSource(): PagingSource<Int, LocalMovie>

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}
