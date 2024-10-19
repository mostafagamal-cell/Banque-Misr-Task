package banquemisr.challenge05.mostafa

import android.os.Bundle
import android.os.Message
import android.telecom.Call.Details
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import banquemisr.challenge05.mostafa.detailsscreen.DetailScreen
import banquemisr.challenge05.mostafa.di.UiStates
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.popular.PopularScreen
import banquemisr.challenge05.mostafa.remotedatasource.RemoteDataSource
import banquemisr.challenge05.mostafa.repo.Repo
import banquemisr.challenge05.mostafa.ui.theme.MostafaTheme
import banquemisr.challenge05.mostafa.viewmodel.DetailFac
import banquemisr.challenge05.mostafa.viewmodel.DetialViewModel
import banquemisr.challenge05.mostafa.viewmodel.PopularFac
import banquemisr.challenge05.mostafa.viewmodel.PopularViewModel
import coil3.compose.AsyncImage
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MostafaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   nav(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Composable
fun nav(modifier: Modifier){
    val navController = rememberNavController()
    val repo= Repo(RemoteDataSource())
    val popularFac= PopularFac(repo)
    val detailFac= DetailFac(repo)
    val popularViewModel= viewModel<PopularViewModel>(factory = popularFac)
    val detailViewModel= viewModel<DetialViewModel>(factory = detailFac)
    NavHost(navController = navController, startDestination = Destination.POPULAR.route) {
        composable(Destination.POPULAR.route) {
            PopularScreen(navController=navController,modifier = modifier,viewModel = popularViewModel)
        }
        composable(Destination.DETAIL.route) {
            val encodedMovieJson = it.arguments?.getString("id")
            val movieJson = encodedMovieJson?.let {e->
                URLDecoder.decode(e, StandardCharsets.UTF_8.toString())
            }
            val movie = Gson().fromJson(movieJson, Results::class.java)
            Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaee", "nav: $id")
            DetailScreen(navController=navController,modifier = modifier, viewModel =detailViewModel, results = movie)
        }
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
    data object POPULAR:Destination(route=Screens.POPULAR)
    data object UPCOMING:Destination(route=Screens.UPCOMING)
    data object DETAIL:Destination(route="${Screens.DETAIL}/{id}"){
      fun createRoute(id:String)= "${Screens.DETAIL}/$id"
    }
}
