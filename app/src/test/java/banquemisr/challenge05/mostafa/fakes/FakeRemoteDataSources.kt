package banquemisr.challenge05.mostafa.fakes

import banquemisr.challenge05.mostafa.dummydata.generateDummyResults
import banquemisr.challenge05.mostafa.pojos.MovieResponse
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.remotedatasource.IRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeRemoteDataSources: IRemoteDataSource {
    override suspend fun getNowPlaying(page: Long): Flow<MovieResponse> {
       return flowOf(generateDummyResults()[page.toInt()])
    }
    override suspend fun getPopular(page: Long): Flow<MovieResponse> {
        return flowOf(generateDummyResults()[page.toInt()])
    }

    override suspend fun getUpcoming(page: Long): Flow<MovieResponse> {
        return flowOf(generateDummyResults()[page.toInt()])
    }

    override suspend fun getDetails(id: Long): Flow<Results> {
        return flowOf(generateDummyResults()[0].results.find { it.id == id }!!)
    }
}