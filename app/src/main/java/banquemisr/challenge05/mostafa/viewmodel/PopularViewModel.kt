package banquemisr.challenge05.mostafa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import banquemisr.challenge05.mostafa.pagging.PopularPaggingSource
import banquemisr.challenge05.mostafa.remotedatasource.RemoteDataSource
import banquemisr.challenge05.mostafa.repo.Repo
import org.koin.java.KoinJavaComponent.inject

class PopularViewModel(private val repo:Repo): ViewModel() {
    val popular = Pager(config = PagingConfig(pageSize = 20, enablePlaceholders = false)) { repo.getPopular() }.flow.cachedIn(viewModelScope)

}