package banquemisr.challenge05.mostafa.repo

import banquemisr.challenge05.mostafa.pagging.*
import banquemisr.challenge05.mostafa.remotedatasource.RemoteDataSource

class Repo(val remoteDataSource: RemoteDataSource) {
     fun getNowPlaying() =GetNowPlayingPaggingSource(remoteDataSource)
     fun getPopular() =PopularPaggingSource(remoteDataSource)
     fun getUpcoming() =UpcomingPaggingSource(remoteDataSource)
}