package dev.belalkhan.cinemate.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import dev.belalkhan.cinemate.R
import dev.belalkhan.cinemate.ui.theme.CinematePreview
import dev.belalkhan.cinemate.ui.theme.CinemateTheme
import dev.belalkhan.cinemate.ui.theme.StarRatingBar

@Composable
fun MoviesScreen() {

}


@Composable
fun MovieList(movies: List<Movie>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(movies) {
            MovieItem(movie = it)
        }
    }
}


@Composable
fun MovieItem(movie: Movie) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(vertical = 24.dp)
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
                    .padding(start = 195.dp)
            ) {
                Text(
                    text = movie.name,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                StarRatingBar(modifier = Modifier.padding(vertical = 16.dp), rating = movie.rating)

                Text(
                    color = Color(0xFF415BE9),
                    text = "${movie.voteCount} Votes",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Image(
            painter = painterResource(id = R.drawable.demo_cover),
            contentDescription = movie.name,
            modifier = Modifier
                .width(194.dp)
                .aspectRatio(0.85f)
                .constrainAs(cover) {
                    start.linkTo(card.start, 8.dp)
                    bottom.linkTo(card.bottom, 18.dp)
                }
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
                    name = "Fast X",
                    cover = "https://dummycoverurl.com",
                    rating = 4f,
                    voteCount = "2k"
                )
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
                    id = 1,
                    name = "Fast X",
                    cover = "https://dummycoverurl.com",
                    rating = 4f,
                    voteCount = "2k"
                )
            }
            MovieList(movies)
        }
    }
}