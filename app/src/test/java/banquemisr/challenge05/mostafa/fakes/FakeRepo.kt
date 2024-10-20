package banquemisr.challenge05.mostafa.fakes

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import banquemisr.challenge05.mostafa.pagging.GetNowPlayingPaggingSource
import banquemisr.challenge05.mostafa.pagging.PopularPaggingSource
import banquemisr.challenge05.mostafa.pagging.UpcomingPaggingSource
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.remotedatasource.IRemoteDataSource
import banquemisr.challenge05.mostafa.repo.IRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class FakeRepo(val remoteDataSources: IRemoteDataSource):IRepo {
    override fun getNowPlaying(): Flow<PagingData<Results>> {
        
        return Pager( config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { GetNowPlayingPaggingSource(remoteDataSources) }).flow
    }

    override fun getPopular(): Flow<PagingData<Results>> {
        return Pager( config = PagingConfig(pageSize = 20, prefetchDistance = 2),
            pagingSourceFactory = { PopularPaggingSource(remoteDataSources) }).flow
    }

    override fun getUpcoming(): Flow<PagingData<Results>> {
        return Pager( config = PagingConfig(pageSize = 20, prefetchDistance = 2), pagingSourceFactory = {
            UpcomingPaggingSource(remoteDataSources)
        }).flow
    }

    override suspend fun getDetail(id: Int): Flow<Results> {
        return remoteDataSources.getDetails(id.toLong())
    }
}