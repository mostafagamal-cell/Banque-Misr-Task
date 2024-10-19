package banquemisr.challenge05.mostafa.pojos

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MovieResponse (
  @SerializedName("page"          ) var page         : Long?               = null,
  @SerializedName("results"       ) var results      : ArrayList<Results> = arrayListOf(),
  @SerializedName("total_pages"   ) var totalPages   : Long?               = null,
  @SerializedName("total_results" ) var totalResults : Long?               = null

)