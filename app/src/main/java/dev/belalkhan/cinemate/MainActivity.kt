package dev.belalkhan.cinemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.belalkhan.cinemate.movies.MoviesScreen
import dev.belalkhan.cinemate.ui.theme.CinemateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinemateTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MoviesScreen()
                }
            }
        }
    }
}
