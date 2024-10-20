package banquemisr.challenge05.mostafa.repo

import androidx.paging.PagingData
import banquemisr.challenge05.mostafa.pojos.Results
import kotlinx.coroutines.flow.Flow

interface IRepo {
    fun getNowPlaying(): Flow<PagingData<Results>>
    fun getPopular(): Flow<PagingData<Results>>
    fun getUpcoming(): Flow<PagingData<Results>>

    suspend fun getDetail(id: Int): Flow<Results>
}