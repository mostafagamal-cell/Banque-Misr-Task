package banquemisr.challenge05.mostafa.fakerepotest

import androidx.paging.map
import androidx.test.ext.junit.runners.AndroidJUnit4
import banquemisr.challenge05.mostafa.fakes.FakeRemoteDataSources
import banquemisr.challenge05.mostafa.fakes.FakeRepo
import banquemisr.challenge05.mostafa.repo.IRepo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestFakeRepo {
    lateinit var fakeRepo: IRepo
    @Before
    fun setUp() {
        fakeRepo = FakeRepo(FakeRemoteDataSources())
    }
    @Test
    fun test_getNowPlaying()=runTest {
        val response = fakeRepo.getNowPlaying()
        val page = response.firstOrNull()
        assert(page !=null)
    }
    @Test
    fun get_detail()=runTest {
        val response = fakeRepo.getDetail(1)
        val page = response.firstOrNull()
        assert(page !=null)
    }
    @Test
    fun get_detail_fail()=runTest {

        try {
            val response = fakeRepo.getDetail(852).first()
        }catch (e:Exception) {
            assert(e.message ==  "Not Found")
        }
    }
}
