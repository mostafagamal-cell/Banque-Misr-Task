package banquemisr.challenge05.mostafa.pagging

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.remotedatasource.IRemoteDataSource
import banquemisr.challenge05.mostafa.remotedatasource.RemoteDataSource
import kotlinx.coroutines.flow.first
import java.io.IOException

class UpcomingPaggingSource(private val remoteDataSource: IRemoteDataSource) : PagingSource<Long, Results>() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Results> {
        return try {
            val currentPage = params.key ?: 1L
            val movies = remoteDataSource.getUpcoming(currentPage)
            LoadResult.Page(
                data = movies.first().results,
                prevKey = if (currentPage == 1L) null else currentPage - 1L,
                nextKey = if (movies.first().results.isEmpty()) null
                else movies.first().page!! + 1L
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Long, Results>): Long? {
        return state.anchorPosition?.toLong()
    }

}