package banquemisr.challenge05.mostafa

import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.runBlocking


class PagingDataEqualityWrapper<T : Any>(private val pagingData: PagingData<T>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PagingDataEqualityWrapper<*>) return false

        val thisList = pagingData.toList()
        val otherList = (other as PagingDataEqualityWrapper<T>).pagingData.toList()

        return thisList == otherList
    }

    override fun hashCode(): Int {
        return pagingData.hashCode()
    }

    private fun PagingData<T>.toList(): List<T> = runBlocking {
        val items = mutableListOf<T>()
        this@toList.map { items.add(it) }
        items
    }
}
fun <T : Any> assertPagingDataEquals(expected: PagingData<T>, actual: PagingData<T>) {
    assert(PagingDataEqualityWrapper(expected) == PagingDataEqualityWrapper(actual)) {
        "PagingData instances are not equal"
    }
}
