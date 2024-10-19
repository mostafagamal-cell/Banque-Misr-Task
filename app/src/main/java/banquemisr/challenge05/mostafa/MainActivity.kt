package banquemisr.challenge05.mostafa

import android.os.Bundle
import android.telecom.Call.Details
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import banquemisr.challenge05.mostafa.ui.theme.MostafaTheme
import banquemisr.challenge05.mostafa.viewmodel.PopularViewModel
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

    LazyColumn(modifier.fillMaxSize()) {
        items(lazyPagingItems.itemCount) { movie ->
            // Check if the movie item is not null before using it
            movie.let {
                lazyPagingItems[movie]?.let { it1 ->
                    Item(
                        title = it1.title ?: "Unknown Title",
                        image = it1.posterPath ?: "default_image_url"
                    )
                }
            }
        }

        // Add a loading state
        when {
            lazyPagingItems.loadState.refresh is LoadState.Loading -> {
                item {
                    // Display a loading indicator
                    CircularProgressIndicator()
                }
            }
            lazyPagingItems.loadState.append is LoadState.Loading -> {
                item {
                    // Display a loading indicator for more items
                    CircularProgressIndicator()
                }
            }
        }

        // Handle errors
        lazyPagingItems.loadState.refresh.let { loadState ->
            if (loadState is LoadState.Error) {
                item {
                    Text(
                        text = "Error: ${loadState.error.localizedMessage}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
@Composable
fun Item(title:String,image:String){
    Card() {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = title)
            Text(text = image)
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
