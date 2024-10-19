package banquemisr.challenge05.mostafa.remotedatasource

import android.util.Log
import banquemisr.challenge05.mostafa.internet.api

import banquemisr.challenge05.mostafa.pojos.MovieResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoteDataSource {
    suspend fun getNowPlaying(page:Long) =flowOf(api.getNowPlaying(page))
    suspend fun getPopular(page:Long): Flow<MovieResponse?> {
        val data=api.getPopular(page.toInt())
        return flowOf(data.body())
    }
    suspend fun getUpcoming(page:Long) =flowOf(api.getUpcoming(page))
    suspend fun getDetails(id:Long) =flowOf(api.getDetails(id))
}