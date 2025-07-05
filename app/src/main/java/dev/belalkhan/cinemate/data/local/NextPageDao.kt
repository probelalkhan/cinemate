package dev.belalkhan.cinemate.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NextPageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(nextPage: NextPage)

    @Query("SELECT * FROM next_page WHERE id = $NEXT_PAGE_ID")
    suspend fun getNextPage(): NextPage?

    @Query("DELETE FROM next_page WHERE id = $NEXT_PAGE_ID")
    suspend fun clear()
}
