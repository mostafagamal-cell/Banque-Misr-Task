package banquemisr.challenge05.mostafa.popular

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import banquemisr.challenge05.mostafa.ConnectivityListener
import banquemisr.challenge05.mostafa.Destination
import banquemisr.challenge05.mostafa.R
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.ui.theme.MostafaTheme
import banquemisr.challenge05.mostafa.viewmodel.PopularViewModel
import coil3.compose.AsyncImage
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun ContentScreen(viewModel: PopularViewModel, modifier: Modifier = Modifier,navController: NavController) {

        val context = LocalContext.current
        val connectivityObserver = remember { ConnectivityListener(context) }
        val isConnected by connectivityObserver.isConnected.collectAsState()
        var lazyPagingItems = viewModel.popular.collectAsLazyPagingItems()
        val selected by viewModel.selectedTabIndex.collectAsState()
        val popularstate by viewModel.popularStateFlow.collectAsState()
        val nowPlayingstate by viewModel.nowPlayingStateFlow.collectAsState()
        val upcomingstate by viewModel.upcomingStateFlow.collectAsState()

     Column(
        modifier = modifier.fillMaxSize(),

    ) {

        TabRow(
            selectedTabIndex = selected,
            modifier = Modifier.fillMaxWidth(),

        ){
            Tab(selected = selected == 0, onClick =  {
                viewModel.selectTab(0)

                    },){
                Text(text = "Popular", fontSize = 20.sp)
                Spacer(Modifier.height(16.dp))

            }
            Tab(selected =  selected == 1, onClick = {viewModel.selectTab(1) },){
                Text(text = "Now Playing", fontSize = 20.sp)
                Spacer(Modifier.height(16.dp))

            }
            Tab(selected =  selected == 2, onClick = { viewModel.selectTab(2) },){
                Text(text = "Upcoming", fontSize = 20.sp)
                Spacer(Modifier.height(16.dp))
            }
        }
         when(selected) {
             0 -> {
                 lazyPagingItems = viewModel.popular.collectAsLazyPagingItems()
                 Spacer(modifier = Modifier.fillMaxHeight(.1f))
                 if (isConnected) {
                     Log.d ("sssssssssssssssssssssssss", "$isConnected")
                     lazyPagingItems.retry()
                 }
                 ScreenContent(lazyPagingItems, navController,popularstate)
             }

             1 -> {
                 lazyPagingItems = viewModel.nowPlaying.collectAsLazyPagingItems()
                 Spacer(modifier = Modifier.fillMaxHeight(.1f))
                 if (isConnected) {
                     Log.d ("sssssssssssssssssssssssss", "$isConnected")
                     lazyPagingItems.retry()
                 }
                 ScreenContent(lazyPagingItems, navController,nowPlayingstate)
             }

             2 -> {
                 lazyPagingItems = viewModel.upcoming.collectAsLazyPagingItems()
                 Spacer(modifier = Modifier.fillMaxHeight(.1f))
                 if (isConnected) {
                     Log.d ("sssssssssssssssssssssssss", "$isConnected")
                     lazyPagingItems.retry()
                 }
                 ScreenContent(lazyPagingItems, navController,upcomingstate)

             }
         }

    }
}

@Composable
private fun ScreenContent(
    lazyPagingItems: LazyPagingItems<Results>,
    navController: NavController,state:LazyListState
) {
    Log.d("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee", "ScreenContent")
    LazyRow(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight().testTag("LazyRow"),
            horizontalArrangement = Arrangement.Center
        ) {
            items(lazyPagingItems.itemCount) { movie ->
                movie.let {
                    lazyPagingItems[movie]?.let { it1 ->
                        Spacer(modifier = Modifier.width(16.dp))
                        Item(
                            move = it1, navController,
                            title = it1.title ?: "Unknown Title",
                            image = it1.posterPath ?: "default_image_url"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
            when {
                lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                lazyPagingItems.loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                lazyPagingItems.loadState.append is LoadState.Error -> {
                    val error = (lazyPagingItems.loadState.append as LoadState.Error).error
                    item {
                        ShowErrorMessage(error.message.toString())
                    }
                }
            }
            lazyPagingItems.loadState.refresh.let { loadState ->
                if (loadState is LoadState.Error) {
                    item {
                        ShowErrorMessage(loadState.error.message.toString())
                    }
                }
            }
        }

}

@Composable
fun ShowErrorMessage(message: String){
    Log.d("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee",message.toString())
    Card(Modifier.width(200.dp)) {
        Text(
            text = "Error: $message",
            modifier = Modifier
                .padding(16.dp).testTag("ErrorTest"),
            color = Color.Red,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
@Composable
fun Item(move: Results, nav:NavController, title:String, image:String){
    val animatedAlpha = remember { Animatable(0.3f) }
    LaunchedEffect(Unit) {
        animatedAlpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }
    Card(modifier = Modifier.clickable {
        val gson = com.google.gson.Gson().toJson(move)
        val encodedMovieJson = URLEncoder.encode(gson, StandardCharsets.UTF_8.toString())
        nav.navigate(Destination.DETAIL.createRoute(encodedMovieJson))
    }.testTag("item")) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(modifier = Modifier.graphicsLayer { alpha = animatedAlpha.value },placeholder = painterResource(id = R.drawable.loading) ,model = "https://image.tmdb.org/t/p/w500$image", contentDescription = "moviePoster",)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = move.releaseDate.toString())
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MostafaTheme {
    }
}
