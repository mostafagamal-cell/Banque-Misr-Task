package banquemisr.challenge05.mostafa.detailsscreen

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import banquemisr.challenge05.mostafa.R
import banquemisr.challenge05.mostafa.di.UiStates
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.viewmodel.DetialViewModel
import coil3.compose.AsyncImage

@Composable
fun DetailScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: DetialViewModel,results: Results) {
   LaunchedEffect(Unit) {
       viewModel.getDetail(results.id!!.toInt())
   }
    val movie =viewModel.detailState.collectAsState()
    Column(modifier = modifier.fillMaxSize()) {
        when (movie.value) {
            is UiStates.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }
            is UiStates.Success -> {
                val data = (movie.value as UiStates.Success).data
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    AsyncImage(
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(.5f),
                        placeholder = painterResource(id = R.drawable.loading),
                        model = "https://image.tmdb.org/t/p/w500${data.posterPath}",
                        contentDescription = "moviePoster"
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(modifier = modifier.fillMaxWidth().fillMaxHeight(.1f), Arrangement.Center) {
                        Text(text = "Title: ${data.title}")
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(modifier = modifier.fillMaxWidth().padding(15.dp), Arrangement.Center) {
                        Text(text = "Overview: ${data.overview}")
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(modifier = modifier.fillMaxWidth().padding(15.dp), Arrangement.Center) {
                        Text(text = "Release Date: ${data.releaseDate}")
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(modifier = modifier.fillMaxWidth().padding(15.dp), Arrangement.Center) {
                        Text(text = "Vote Average: ${data.voteAverage}")
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    Row(modifier = modifier.fillMaxWidth().padding(15.dp), Arrangement.Center) {
                        Text(text = "Original Language: ${data.originalLanguage}")
                    }
                }
            }

            is UiStates.Error -> {
                val error = (movie.value as UiStates.Error).message
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = "Error: $error", textAlign = TextAlign.Center)
                }

            }
        }
    }

}