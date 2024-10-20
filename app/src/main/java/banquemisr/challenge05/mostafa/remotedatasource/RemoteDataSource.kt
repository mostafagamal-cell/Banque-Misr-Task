package banquemisr.challenge05.mostafa.remotedatasource

import android.util.Log
import banquemisr.challenge05.mostafa.internet.api

import banquemisr.challenge05.mostafa.pojos.MovieResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class RemoteDataSource private constructor() : IRemoteDataSource {
    companion object{
        var remoteDataSource: RemoteDataSource? = null
        fun getInstance(): RemoteDataSource {
            if (remoteDataSource == null) {
                remoteDataSource = RemoteDataSource()
            }
            return remoteDataSource!!
        }
    }
    override suspend fun getNowPlaying(page:Long) =flowOf(api.getNowPlaying(page))
    override suspend fun getPopular(page:Long): Flow<MovieResponse> {
        val data=api.getPopular(page.toInt())
        return flowOf(data.body()!!)
    }
    override suspend fun getUpcoming(page:Long) =flowOf(api.getUpcoming(page))
    override suspend fun getDetails(id:Long) =flowOf(api.getDetails(id))
}