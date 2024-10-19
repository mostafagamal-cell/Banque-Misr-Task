package banquemisr.challenge05.mostafa.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import banquemisr.challenge05.mostafa.pagging.*
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.remotedatasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class Repo(val remoteDataSource: RemoteDataSource) {
     fun getNowPlaying() : Flow<PagingData<Results>> {
          return Pager(
               config = PagingConfig(pageSize = 20, enablePlaceholders = false),
               pagingSourceFactory = { GetNowPlayingPaggingSource(remoteDataSource) }
          ).flow
     }
     fun getPopular(): Flow<PagingData<Results>> {
          return Pager(
               config = PagingConfig(pageSize = 20, enablePlaceholders = false),
               pagingSourceFactory = { PopularPaggingSource(remoteDataSource) }
          ).flow
     }
     fun getUpcoming() : Flow<PagingData<Results>> {
          return Pager(
               config = PagingConfig(pageSize = 20, enablePlaceholders = false),
               pagingSourceFactory = { GetNowPlayingPaggingSource(remoteDataSource) }
          ).flow
     }
     suspend fun getDetail(id:Int): Flow<Results> {
          return remoteDataSource.getDetails(id.toLong())
     }

}