package dev.belalkhan.cinemate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalMovie::class, NextPage::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val nextPageDao: NextPageDao
}
