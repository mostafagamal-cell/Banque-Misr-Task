package banquemisr.challenge05.mostafa

import android.os.Bundle
import android.os.Message
import android.telecom.Call.Details
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import banquemisr.challenge05.mostafa.ui.theme.MostafaTheme
import banquemisr.challenge05.mostafa.viewmodel.PopularViewModel
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.first
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
         val  viewmodel: PopularViewModel by inject()
        setContent {
            MostafaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        viewmodel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(viewModel: PopularViewModel, modifier: Modifier = Modifier) {
    // Collect the lazy paging items from the ViewModel
    val lazyPagingItems = viewModel.popular.collectAsLazyPagingItems()
    val context = LocalContext.current
    val connectivityObserver = remember { ConnectivityListener(context) }
    val isConnected by connectivityObserver.isConnected.collectAsState(true)
    if (isConnected) {
        lazyPagingItems.retry()
    }
    Log.d("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeee","builded")
    LazyColumn(modifier.fillMaxSize()) {
        items(lazyPagingItems.itemCount) { movie ->
            movie.let {
                lazyPagingItems[movie]?.let { it1 ->
                    Item(
                        title = it1.title ?: "Unknown Title",
                        image = it1.posterPath ?: "default_image_url"
                    )
                }
            }
        }
        when {
            lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }
            lazyPagingItems.loadState.append is LoadState.Loading -> {
                item {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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

    Text(
        text = "Error: $message",
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        color = Color.Red,
        textAlign = TextAlign.Center
    )
}
@Composable
fun Item(title:String,image:String){
    Card() {
        Column(modifier = Modifier.fillMaxWidth().fillMaxHeight(.2F),horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(model = "https://image.tmdb.org/t/p/w500$image", contentDescription = "moviePoster")
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

object Screens{
    const val NowPlaying="now playing"
    const val POPULAR="popular"
    const val UPCOMING="upcoming"
    const val DETAIL="Detail"
}
sealed class Destination(val route:String){
    data object NowPlaying:Destination(route=Screens.NowPlaying)
    data object Preview:Destination(route=Screens.POPULAR)
    data object UPCOMING:Destination(route=Screens.UPCOMING)
    data object DETAIL:Destination(route="${Screens.DETAIL}/{id}"){
      fun createRoute(id:Int)= "${Screens.DETAIL}/$id"
    }
}
