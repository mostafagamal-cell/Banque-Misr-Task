package banquemisr.challenge05.mostafa.viewmodelTest

import androidx.test.ext.junit.runners.AndroidJUnit4
import banquemisr.challenge05.mostafa.fakes.FakeRemoteDataSources
import banquemisr.challenge05.mostafa.fakes.FakeRepo
import banquemisr.challenge05.mostafa.remotedatasource.IRemoteDataSource
import banquemisr.challenge05.mostafa.uistates.UiStates
import banquemisr.challenge05.mostafa.viewmodel.DetialViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class DetailViewModelTest {
    private lateinit var viewModel: DetialViewModel
    private lateinit var fakeRepo: FakeRepo
    private lateinit var remote: IRemoteDataSource
    @Before
    fun setUp() {
        remote= FakeRemoteDataSources()
        fakeRepo = FakeRepo(remote)
        viewModel = DetialViewModel(fakeRepo)
    }
    @Test
    fun test_get_detail_success()= runTest{
       launch {
              viewModel.detailState.collect {
                  if (it is UiStates.Success){
                      println(it.data.id)
                      assert(it .data.id ==1L )
                      cancel()
                  }
                  if (it is UiStates.Error){
                      println(it.message)
                      assert(it.message == "Not Found")
                      cancel()
                  }
           }
       }
        viewModel.getDetail(1)
    }
    @Test
    fun test_get_detail_fail()= runTest{
        val response = viewModel.detailState.first()
        assert(response is UiStates.Loading)
        launch {
            viewModel.detailState.collect {
                if (it is UiStates.Error){
                    println(it.message)
                    assert(it.message == "Not Found")
                    cancel()
                }
            }
        }
        viewModel.getDetail(100)

    }
}