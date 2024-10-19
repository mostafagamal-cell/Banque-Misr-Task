package banquemisr.challenge05.mostafa.remotedatasource

import android.util.Log
import banquemisr.challenge05.mostafa.internet.api
import banquemisr.challenge05.mostafa.internet.getapi
import banquemisr.challenge05.mostafa.pojos.MovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoteDataSource {
    suspend fun getNowPlaying(page:Long) =flowOf(getapi().getNowPlaying(page))
    suspend fun getPopular(page:Long): Flow<MovieResponse?> {
        val data=getapi().getPopular(page.toInt())

        Log.d("asdasdasdadasdsdasd",data.body().toString())
       return flowOf(data.body())
    }
    suspend fun getUpcoming(page:Long) =flowOf(getapi().getUpcoming(page))
    suspend fun getDetails(id:Long) =flowOf(getapi().getDetails(id))
}