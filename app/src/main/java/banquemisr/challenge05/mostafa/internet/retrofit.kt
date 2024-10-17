package banquemisr.challenge05.mostafa.internet

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit

val request= Request.Builder()
    .addHeader("accept", "application/json")
    .addHeader("Authorization","Bearer 7de1c0537b6145c029b7f92f6c929839").build()
val client=OkHttpClient().newBuilder().addInterceptor {
    chain: Interceptor.Chain -> chain.proceed(request)
}.build()
val retrofit= Retrofit.Builder().client(client)
