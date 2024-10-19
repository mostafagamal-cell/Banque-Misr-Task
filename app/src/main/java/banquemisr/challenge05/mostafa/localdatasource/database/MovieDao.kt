package banquemisr.challenge05.mostafa.localdatasource.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import banquemisr.challenge05.mostafa.pojos.MovieResponse
import banquemisr.challenge05.mostafa.pojos.Results

//@Dao
//interface MovieDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertMovies(movies: List<Results>)
//    @Query("DELETE FROM favourites")
//    suspend fun clearAll()
//}