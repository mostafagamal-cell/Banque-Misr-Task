package banquemisr.challenge05.mostafa

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import banquemisr.challenge05.mostafa.detailsscreen.DetailScreen
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.popular.ContentScreen
import banquemisr.challenge05.mostafa.remotedatasource.RemoteDataSource
import banquemisr.challenge05.mostafa.repo.Repo
import banquemisr.challenge05.mostafa.ui.theme.MostafaTheme
import banquemisr.challenge05.mostafa.viewmodel.DetailFac
import banquemisr.challenge05.mostafa.viewmodel.DetialViewModel
import banquemisr.challenge05.mostafa.viewmodel.PopularFac
import banquemisr.challenge05.mostafa.viewmodel.PopularViewModel
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MostafaTheme {
                MainScreen()
            }
        }
    }
}
@Composable
fun MainScreen(
) {
    val title = rememberSaveable { mutableStateOf("Movies") }
    val navController = rememberNavController()
    LaunchedEffect(Unit) {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d("aaaaaaaaaaaaaaaaaaaaaaaaaaee", "onCreate: ${destination.route}")
            when (destination.route) {
                Destination.MainScreen.route -> {
                    title.value = "Movies"
                }
            }
        }
    }
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (title.value != "Movies") {
                    IconButton(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxHeight().testTag("backButton"),
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                    Text(text = title.value, fontSize = 25.sp)
                } else {
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = title.value, fontSize = 25.sp)
                    }
                }
            }
            nav(modifier = Modifier.padding(innerPadding), navController, title)
        }

    }
}
@Composable
fun nav(modifier: Modifier,navController: NavHostController,title:MutableState<String>){
    val repo= Repo.getInstance(RemoteDataSource.getInstance())
    val popularFac= PopularFac(repo)
    val detailFac= DetailFac(repo)
    val popularViewModel= viewModel<PopularViewModel>(factory = popularFac)
    val detailViewModel= viewModel<DetialViewModel>(factory = detailFac)
    NavHost(navController = navController, startDestination = Destination.MainScreen.route) {
        composable(Destination.MainScreen.route) {
            ContentScreen(navController=navController,modifier = modifier,viewModel = popularViewModel)
        }
        composable(Destination.DETAIL.route) {
            val encodedMovieJson = it.arguments?.getString("id")
            val movieJson = encodedMovieJson?.let {e->
                URLDecoder.decode(e, StandardCharsets.UTF_8.toString())
            }

            val movie = Gson().fromJson(movieJson, Results::class.java)
            LaunchedEffect(Unit) {
                title.value = movie.title.toString()
                Log.d("dasdasdasddsadasd", "nav: $id")

            }
            DetailScreen(navController=navController,modifier = modifier, viewModel =detailViewModel, results = movie)
        }
    }

}

object Screens{
    const val Main="Main"
    const val DETAIL="Detail"
}
sealed class Destination(val route:String){
    data object MainScreen:Destination(route=Screens.Main)
    data object DETAIL:Destination(route="${Screens.DETAIL}/{id}"){
      fun createRoute(id:String)= "${Screens.DETAIL}/$id"
    }
}
