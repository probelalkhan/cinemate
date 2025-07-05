package dev.belalkhan.cinemate.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import dev.belalkhan.cinemate.ui.theme.CinematePreview
import dev.belalkhan.cinemate.ui.theme.CinemateTheme
import dev.belalkhan.cinemate.ui.theme.StarRatingBar
import kotlinx.coroutines.flow.flowOf

@Composable
fun MoviesScreen(viewModel: MovieViewModel = hiltViewModel()) {
    val moviesPagingItems = viewModel.pagingDataFlow.collectAsLazyPagingItems()
    MovieList(movies = moviesPagingItems)
}

@Composable
fun MovieList(movies: LazyPagingItems<Movie>) {
    val refreshing = movies.loadState.refresh is LoadState.Loading
    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = refreshing,
        onRefresh = { movies.refresh() },
    ) {
        when (val state = movies.loadState.refresh) {
            is LoadState.Error -> {
                ErrorView(
                    message = "Something Went Wrong\n\n${state.error.localizedMessage}",
                    onRetry = { movies.refresh() },
                )
            }

            LoadState.Loading -> Unit
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(
                        count = movies.itemCount,
                        key = movies.itemKey { it.id },
                    ) { index ->
                        val movie = movies[index]
                        if (movie != null) {
                            MovieItem(movie = movie)
                        }
                    }
                    item {
                        if (movies.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(vertical = 8.dp)
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(vertical = 24.dp),
    ) {
        val (cover, card) = createRefs()

        Box(
            modifier = Modifier
                .height(170.dp)
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .constrainAs(card) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                },
        ) {
            Column(
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .fillMaxWidth()
                    .padding(start = 195.dp),
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                StarRatingBar(modifier = Modifier.padding(vertical = 16.dp), rating = movie.rating)

                Text(
                    color = Color(0xFF415BE9),
                    text = movie.voteCount,
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }

        AsyncImage(
            model = movie.cover,
            contentDescription = movie.title,
            modifier = Modifier
                .width(194.dp)
                .aspectRatio(0.85f)
                .constrainAs(cover) {
                    start.linkTo(card.start, 8.dp)
                    bottom.linkTo(card.bottom, 18.dp)
                },
        )
    }
}

@CinematePreview
@Composable
private fun MovieItemPreview() {
    CinemateTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            MovieItem(
                Movie(
                    id = 1,
                    title = "Fast X",
                    cover = "https://dummycoverurl.com",
                    rating = 4f,
                    voteCount = "2k",
                ),
            )
        }
    }
}

@CinematePreview
@Composable
private fun MovieListPreview() {
    CinemateTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            val movies = (0..10).map {
                Movie(
                    id = it,
                    title = "Fast X",
                    cover = "https://dummycoverurl.com",
                    rating = 4f,
                    voteCount = "2k",
                )
            }
            val items = PagingData.from(movies)
            MovieList(movies = flowOf(items).collectAsLazyPagingItems())
        }
    }
}
