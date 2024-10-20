package banquemisr.challenge05.mostafa.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import banquemisr.challenge05.mostafa.pagging.*
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.remotedatasource.IRemoteDataSource
import banquemisr.challenge05.mostafa.remotedatasource.RemoteDataSource
import kotlinx.coroutines.flow.Flow

class Repo private constructor(val remoteDataSource: IRemoteDataSource) : IRepo {
     companion object{
          private var instance: Repo? = null
          fun getInstance(remoteDataSource: RemoteDataSource): Repo {
               if (instance == null) {
                    instance = Repo(remoteDataSource)
               }
               return instance!!
          }
     }
     override fun getNowPlaying() : Flow<PagingData<Results>> {
          return Pager(
               config = PagingConfig(pageSize = 20, enablePlaceholders = false),
               pagingSourceFactory = { GetNowPlayingPaggingSource(remoteDataSource) }
          ).flow
     }
     override fun getPopular(): Flow<PagingData<Results>> {
          return Pager(
               config = PagingConfig(pageSize = 20, enablePlaceholders = false),
               pagingSourceFactory = { PopularPaggingSource(remoteDataSource) }
          ).flow
     }
     override fun getUpcoming() : Flow<PagingData<Results>> {
          return Pager(
               config = PagingConfig(pageSize = 20, enablePlaceholders = false),
               pagingSourceFactory = { UpcomingPaggingSource(remoteDataSource) }
          ).flow
     }
     override suspend fun getDetail(id:Int): Flow<Results> {
          return remoteDataSource.getDetails(id.toLong())
     }

}