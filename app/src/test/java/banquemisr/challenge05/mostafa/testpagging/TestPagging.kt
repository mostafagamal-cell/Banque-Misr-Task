package banquemisr.challenge05.mostafa.testpagging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import androidx.test.ext.junit.runners.AndroidJUnit4
import banquemisr.challenge05.mostafa.dummydata.generateDummyResults
import banquemisr.challenge05.mostafa.pagging.GetNowPlayingPaggingSource
import banquemisr.challenge05.mostafa.pagging.PopularPaggingSource
import banquemisr.challenge05.mostafa.pagging.UpcomingPaggingSource
import banquemisr.challenge05.mostafa.remotedatasource.IRemoteDataSource
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class TestPagging {
    @Mock
    var remote: IRemoteDataSource?=null
    lateinit var getNowPagging: GetNowPlayingPaggingSource
    lateinit var getPopularPagging: PopularPaggingSource
    lateinit var getUpcomingPagging: UpcomingPaggingSource
    @Before
    fun set_Up(){
        MockitoAnnotations.openMocks(this)
        remote = Mockito.mock(IRemoteDataSource::class.java);
        getPopularPagging = PopularPaggingSource(remote!!)
        getNowPagging = GetNowPlayingPaggingSource(remote!!)

        getUpcomingPagging = UpcomingPaggingSource(remote!!)
    }
    @Test
    fun test_getNowPlaying()= runTest{
        `when`(remote!!.getNowPlaying(1)).thenReturn(flowOf(generateDummyResults()[1]))
        `when`(remote!!.getNowPlaying(2)).thenReturn(flowOf(generateDummyResults()[2]))
        `when`(remote!!.getNowPlaying(3)).thenThrow(RuntimeException("Error"))
        val pager= TestPager(PagingConfig(10),getNowPagging)
        assert(pager.refresh() is PagingSource.LoadResult.Page)
        val page= pager.getLastLoadedPage()
        assert(page != null)
        assert(page?.prevKey == null)
        assert(page?.nextKey == 2L)
        pager.append()
        val page2= pager.getLastLoadedPage()
        assert(page2?.nextKey == 3L)
        assert(pager.append() is PagingSource.LoadResult.Error)
    }
    @Test
    fun test_get_popular()=runTest {
        `when`(remote!!.getPopular(1)).thenReturn(flowOf(generateDummyResults()[1]))
        `when`(remote!!.getPopular(2)).thenReturn(flowOf(generateDummyResults()[2]))
        `when`(remote!!.getPopular(3)).thenThrow(RuntimeException("Error"))
        val pager= TestPager(PagingConfig(10),getPopularPagging)
        val x= pager.refresh()
        assert(x is PagingSource.LoadResult.Page)
        val page= pager.getLastLoadedPage()
        assert(page != null)
        assert(page?.prevKey == null)
        assert(page?.nextKey == 2L)
        assert(pager.append() is PagingSource.LoadResult.Page)
        val page2= pager.getLastLoadedPage()
        assert(page2 != null)
        assert(page2?.prevKey == 1L)
        assert(page2?.nextKey == 3L)
        assert(pager.append() is PagingSource.LoadResult.Error)
    }
    @After
    fun clear(){
        remote = null
    }

    @Test
    fun test_get_upcoming()=runTest {
        `when`(remote!!.getUpcoming(1)).thenReturn(flowOf(generateDummyResults()[1]))
        `when`(remote!!.getUpcoming(2)).thenReturn(flowOf(generateDummyResults()[2]))
        `when`(remote!!.getUpcoming(3)).thenThrow(RuntimeException("Error"))
        val pager= TestPager(PagingConfig(10),getUpcomingPagging)
        val x= pager.refresh()
        assert(x is PagingSource.LoadResult.Page)
        val page= pager.getLastLoadedPage()
        assert(page != null)
        assert(page?.prevKey == null)
        assert(page?.nextKey == 2L)
        assert(pager.append() is PagingSource.LoadResult.Page)
        val page2= pager.getLastLoadedPage()
        assert(page2 != null)
        assert(page2?.prevKey == 1L)
        assert(page2?.nextKey == 3L)
        assert(pager.append() is PagingSource.LoadResult.Error)
    }


}