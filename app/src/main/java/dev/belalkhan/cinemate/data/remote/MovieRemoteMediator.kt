package dev.belalkhan.cinemate.data.remote

import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import dev.belalkhan.cinemate.data.local.LocalMovie
import dev.belalkhan.cinemate.data.local.MovieDatabase
import dev.belalkhan.cinemate.data.local.NextPage
import dev.belalkhan.cinemate.data.toLocalMovie
import javax.inject.Inject

class MovieRemoteMediator @Inject constructor(
    private val db: MovieDatabase,
    private val repository: MovieRepository,
) : RemoteMediator<Int, LocalMovie>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.SKIP_INITIAL_REFRESH

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, LocalMovie>,
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> db.nextPageDao.getNextPage()?.nextPage ?: 1
            }

            val remoteMovies = repository.getMovies(page = loadKey)
            val nextPage =
                if (remoteMovies.page < remoteMovies.totalPages) remoteMovies.page + 1 else null

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.movieDao.clearAll()
                    db.nextPageDao.clear()
                }
                val localMovies = remoteMovies.movies.map { it.toLocalMovie() }
                db.movieDao.upsertAll(localMovies)
                db.nextPageDao.insertOrReplace(NextPage(nextPage = nextPage))
            }

            return MediatorResult.Success(endOfPaginationReached = nextPage == null)
        } catch (e: Exception) {
            return MediatorResult.Error(
                throwable = e,
            )
        }
    }
}
