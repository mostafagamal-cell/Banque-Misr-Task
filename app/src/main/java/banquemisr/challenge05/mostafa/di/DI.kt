package banquemisr.challenge05.mostafa.di

import androidx.lifecycle.viewmodel.compose.viewModel
import banquemisr.challenge05.mostafa.pagging.GetNowPlayingPaggingSource
import banquemisr.challenge05.mostafa.pagging.PopularPaggingSource
import banquemisr.challenge05.mostafa.pagging.UpcomingPaggingSource
import banquemisr.challenge05.mostafa.remotedatasource.RemoteDataSource
import banquemisr.challenge05.mostafa.repo.Repo
import banquemisr.challenge05.mostafa.viewmodel.PopularViewModel
import org.koin.androidx.compose.get
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        RemoteDataSource()
    }
    single {
        GetNowPlayingPaggingSource(get())
    }
    single {
        PopularPaggingSource(get())
    }
    single {
        UpcomingPaggingSource(get())
    }
    single {
        Repo(get())
    }
    viewModel { PopularViewModel(get()) }


}