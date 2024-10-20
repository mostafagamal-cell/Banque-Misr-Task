package banquemisr.challenge05.mostafa.remotedatasource

import banquemisr.challenge05.mostafa.pojos.MovieResponse
import banquemisr.challenge05.mostafa.pojos.Results
import kotlinx.coroutines.flow.Flow

interface IRemoteDataSource {
    suspend fun getNowPlaying(page: Long): Flow<MovieResponse>

    suspend fun getPopular(page: Long): Flow<MovieResponse>

    suspend fun getUpcoming(page: Long): Flow<MovieResponse>

    suspend fun getDetails(id: Long): Flow<Results>
}