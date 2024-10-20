package banquemisr.challenge05.mostafa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import banquemisr.challenge05.mostafa.uistates.UiStates
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.repo.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetialViewModel(private val repo: Repo): ViewModel() {
    private var detail=MutableStateFlow<UiStates<Results>>(UiStates.Loading)
    val detailState: StateFlow<UiStates<Results>> = detail
    fun getDetail(id:Int){
        detail.value=UiStates.Loading
       viewModelScope.launch(Dispatchers.IO) {
           try {
          val data= repo.getDetail(id).first()
               detail.value=UiStates.Success(data)
       }catch (e:Exception){
           detail.value=UiStates.Error(e.message.toString())
        }
       }
    }
}
class DetailFac(private val repo: Repo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetialViewModel(repo) as T
    }
}