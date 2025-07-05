package dev.belalkhan.cinemate.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class LocalMovie(
    val id: Long,
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var localId: Int = 0
}
