package banquemisr.challenge05.mostafa.internet

import android.util.Log
import banquemisr.challenge05.mostafa.pojos.MovieResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
const val url="https://api.themoviedb.org/3/movie/"
val client=OkHttpClient().newBuilder().addInterceptor { chain: Interceptor.Chain ->
    val request: Request = chain.request()
    val newRequest: Request = request.newBuilder().addHeader("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNWMwODE0MjkxMWExMTQ5NzQ2NGNiMzM5NzZiZmE2MCIsIm5iZiI6MTcyOTE5NzI2My42MDQzMDIsInN1YiI6IjY2MjM3ZjgxMjU4ODIzMDE3ZDkwYjBjYyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.M6I7a0kXOniiQj6oJxga4oIp6tBLtWG20Y3ZJQxIxQQ").build()
    return@addInterceptor chain.proceed(newRequest)
}.build()
val retrofit= Retrofit.Builder().baseUrl(url).client(client).addConverterFactory(GsonConverterFactory.create()).build()
var api:Api?=null
interface Api{
    @GET("now_playing")
    suspend fun getNowPlaying(@Query("page")page:Long): MovieResponse
    @GET("popular")
    suspend fun getPopular(@Query("page")page:Int): Response<MovieResponse>
    @GET("upcoming")
    suspend fun getUpcoming(@Query("page")page:Long): MovieResponse
    @GET("{id}")
    suspend fun getDetails(@Path("id")id:Long): MovieResponse
}
fun getapi():Api{
    if (api==null){
        api=retrofit.create(Api::class.java)
    }
    return api!!
}