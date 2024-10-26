package banquemisr.challenge05.mostafa.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import banquemisr.challenge05.mostafa.repo.IRepo
import banquemisr.challenge05.mostafa.repo.Repo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PopularViewModel(repo: IRepo): ViewModel() {
    val popular=repo.getPopular().cachedIn(viewModelScope)
    val nowPlaying=repo.getNowPlaying().cachedIn(viewModelScope)
    val upcoming=repo.getUpcoming().cachedIn(viewModelScope)
    private var selectedTab = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = selectedTab
    private var popularState= MutableStateFlow<LazyListState>(LazyListState())
    val popularStateFlow: StateFlow<LazyListState> = popularState
    private var nowPlayingState= MutableStateFlow<LazyListState>(LazyListState())
    val nowPlayingStateFlow: StateFlow<LazyListState> = nowPlayingState
    private var upcomingState= MutableStateFlow<LazyListState>(LazyListState())
    val upcomingStateFlow: StateFlow<LazyListState> = upcomingState
    fun selectTab(index: Int) {
        selectedTab.value = index
    }
}
class PopularFac(private val repo: Repo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PopularViewModel(repo) as T
    }
}