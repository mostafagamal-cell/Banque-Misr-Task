package banquemisr.challenge05.mostafa.viewmodelTest

import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.testing.TestPager
import androidx.test.ext.junit.runners.AndroidJUnit4
import banquemisr.challenge05.mostafa.assertPagingDataEquals
import banquemisr.challenge05.mostafa.dummydata.generateDummyResults
import banquemisr.challenge05.mostafa.dummydata.generateDummyResultsList
import banquemisr.challenge05.mostafa.fakes.FakeRemoteDataSources
import banquemisr.challenge05.mostafa.fakes.FakeRepo
import banquemisr.challenge05.mostafa.pojos.Results
import banquemisr.challenge05.mostafa.remotedatasource.IRemoteDataSource
import banquemisr.challenge05.mostafa.repo.IRepo
import banquemisr.challenge05.mostafa.viewmodel.PopularViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class PopularViewModelTest {
    private lateinit var viewModel: PopularViewModel
    @Mock
    private lateinit var fakeRepo: IRepo
    private lateinit var remote: IRemoteDataSource
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        remote= FakeRemoteDataSources()
        fakeRepo=mock(IRepo::class.java)

    }
    @Test
    fun test_get_now_playing()= runTest{
        `when`(fakeRepo.getNowPlaying()).thenReturn(flowOf(PagingData.from(generateDummyResultsList(1))))
        `when`(fakeRepo.getPopular()).thenReturn(flowOf(PagingData.from(generateDummyResultsList(1))))
        `when`(fakeRepo.getUpcoming()).thenReturn(flowOf(PagingData.from(generateDummyResultsList(1))))
        viewModel = PopularViewModel(fakeRepo)
        verify(fakeRepo).getNowPlaying()
    }
    @Test
    fun test_get_popular()= runTest{
        `when`(fakeRepo.getNowPlaying()).thenReturn(flowOf(PagingData.from(generateDummyResultsList(1))))
        `when`(fakeRepo.getPopular()).thenReturn(flowOf(PagingData.from(generateDummyResultsList(1))))
        `when`(fakeRepo.getUpcoming()).thenReturn(flowOf(PagingData.from(generateDummyResultsList(1))))
        viewModel = PopularViewModel(fakeRepo)
        verify(fakeRepo).getPopular()
    }

    @Test
    fun test_get_upcoming()= runTest{
        `when`(fakeRepo.getNowPlaying()).thenReturn(flowOf(PagingData.from(generateDummyResultsList(1))))
        `when`(fakeRepo.getPopular()).thenReturn(flowOf(PagingData.from(generateDummyResultsList(1))))
        `when`(fakeRepo.getUpcoming()).thenReturn(flowOf(PagingData.from(generateDummyResultsList(1))))
        viewModel = PopularViewModel(fakeRepo)
        verify(fakeRepo).getUpcoming()
    }

}


