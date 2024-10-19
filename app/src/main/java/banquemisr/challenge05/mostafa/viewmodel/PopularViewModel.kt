package banquemisr.challenge05.mostafa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import banquemisr.challenge05.mostafa.di.UiStates
import banquemisr.challenge05.mostafa.pagging.PopularPaggingSource
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.remotedatasource.RemoteDataSource
import banquemisr.challenge05.mostafa.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PopularViewModel(private val repo:Repo): ViewModel() {
    val popular=repo.getPopular().cachedIn(viewModelScope)

}
class PopularFac(val repo: Repo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PopularViewModel(repo) as T
    }
}