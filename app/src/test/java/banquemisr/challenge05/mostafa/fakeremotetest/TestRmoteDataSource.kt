package banquemisr.challenge05.mostafa.fakeremotetest

import androidx.test.ext.junit.runners.AndroidJUnit4
import banquemisr.challenge05.mostafa.fakes.FakeRemoteDataSources
import banquemisr.challenge05.mostafa.remotedatasource.IRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestRmoteDataSource {
   lateinit var remote:IRemoteDataSource
    @Before
    fun setUp() {
        remote = FakeRemoteDataSources()
    }
    @Test
    fun test_getNowPlaying()= runTest{
       val response = remote.getNowPlaying(1).first()
        assert(response.page == 1L)
    }
    @Test
    fun test_getPopular()= runTest{
        val response = remote.getPopular(1).first()
        assert(response.page == 1L)
    }
    @Test
    fun test_getUpcoming()= runTest{
        val response = remote.getUpcoming(1).first()
        assert(response.page == 1L)
    }
    @Test
    fun test_getDetails()= runTest{
        val response = remote.getDetails(1).first()
        assert(response.id == 1L)
    }
    @Test
    fun test_get_page2_nowPlaying()= runTest{
        val response = remote.getNowPlaying(2).first()
        assert(response.page == 2L)
    }
    @Test
    fun test_get_page2_popular()= runTest{
        val response = remote.getPopular(2).first()
        assert(response.page == 2L)
    }
    @Test
    fun test_get_page2_upcoming()= runTest{
        val response = remote.getUpcoming(2).first()
        assert(response.page == 2L)
    }
    @Test
    fun test_get_page2_details()= runTest{
        try {
         remote.getDetails(999).first()
        }catch (e:Exception){
            assert(e is NullPointerException)
        }
    }

}