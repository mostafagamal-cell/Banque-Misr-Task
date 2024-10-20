package banquemisr.challenge05.mostafa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import banquemisr.challenge05.mostafa.repo.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PopularViewModel(repo:Repo): ViewModel() {
    val popular=repo.getPopular().cachedIn(viewModelScope)
    val nowPlaying=repo.getNowPlaying().cachedIn(viewModelScope)
    val upcoming=repo.getUpcoming().cachedIn(viewModelScope)
    private var selectedTab = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = selectedTab
    fun selectTab(index: Int) {
        selectedTab.value = index
    }
}
class PopularFac(private val repo: Repo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PopularViewModel(repo) as T
    }
}