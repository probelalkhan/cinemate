package dev.belalkhan.cinemate.data

import dev.belalkhan.cinemate.data.local.LocalMovie
import dev.belalkhan.cinemate.data.remote.RemoteMovie
import dev.belalkhan.cinemate.movies.Movie

fun RemoteMovie.toLocalMovie() = LocalMovie(
    id = this.id,
    adult = this.adult,
    backdropPath = this.backdropPath,
    genreIds = this.genreIds.joinToString(","),
    originalLanguage = this.originalLanguage,
    originalTitle = this.originalTitle,
    overview = this.overview,
    popularity = this.popularity,
    posterPath = this.posterPath,
    releaseDate = this.releaseDate,
    title = this.title,
    video = this.video,
    voteAverage = this.voteAverage,
    voteCount = this.voteCount,
)

fun LocalMovie.toMovie() = Movie(
    id = this.localId,
    title = this.originalTitle,
    cover = "https://image.tmdb.org/t/p/w500${this.posterPath}",
    rating = (this.voteAverage / 2).toFloat(),
    voteCount = "${this.voteCount.toKFormat()} votes",
)

private fun Int.toKFormat() =
    if (this < 1000) this.toString() else "${this / 1000}${if (this < 1000000) "k" else "M"}"
