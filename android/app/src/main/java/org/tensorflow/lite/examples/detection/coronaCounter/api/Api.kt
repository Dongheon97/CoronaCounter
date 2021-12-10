package org.tensorflow.lite.examples.detection.coronaCounter.api

import com.example.coronacounter.model.Shop
import com.example.coronacounter.model.User
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.tensorflow.lite.examples.detection.coronaCounter.model.Trial
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {
    @Headers("Content-Type:application/json")
    @POST("login")
    suspend fun authentication(@Body user: User)
    : Response<User>

    @Headers("Content-Type:application/json")
    @POST("distance-stage")
    suspend fun getDistance(@Body rname: String)
    : Response<Integer>

    //유저를 건네주면
    //그 유저를 데이터베이스에 추가해서 회원가입을 하고
    // 성공하면 true를, 실패하면 false를 받고 싶음
    @Headers("Content-Type:application/json")
    @POST("add-user")
    suspend fun addUser(@Body user: User)
            : Response<Boolean>

    //유저를 건네주면 그 유저가 가지고 있는
    //상가들을 리스트형태로 받고 싶음.
    @Headers("Content-Type:application/json")
    @POST("shopList")
    suspend fun getShopLists(@Body user: User)
            : Response<List<Shop>>

    //id를 건네주면
    //그 id를 사용하고 있는 유저가 있는지 boolean으로 받고 싶음.
    @Headers("Content-Type:application/json")
    @POST("idValid")
    suspend fun isIdValid(@Body id: String)
            : Response<Boolean>


    //Shop을 건네주면
    //db에 저장하고
    //성공했는지를 boolean으로 받고 싶음
    @Headers("Content-Type:application/json")
    @POST("add-shop")
    suspend fun addShop(@Body shop: Shop)
            : Response<Boolean>


    //Shop을 건네주면
    //db에 저장하고
    //성공했는지를 boolean으로 받고 싶음
    @Headers("Content-Type:application/json")
    @POST("delete-shop")
    suspend fun deleteShop(@Body shop: Shop)
            : Response<Boolean>

    // TODO
    // editshop api 짜줘..
    @Headers("Content-Type:application/json")
    @POST("edit-shop")
    suspend fun editShop(@Body shopInfo: Map<String, Shop>)
            : Response<Boolean>

    // TODO
    // getStatistic api 짜줘...
    @Headers("Content-Type:application/json")
    @POST("statistic")
    suspend fun getStatistic(@Body user: Shop)
            : List<Trial>

}

object RetrofitInstance {
    // 안드로이드는 클라이언트 ip가 다름.
//    val BASE_URL: String = "http://10.0.2.2:8080/"
    val BASE_URL: String = "http://3.13.129.81:8080/"

    val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    val client: OkHttpClient = OkHttpClient.Builder().apply {
        this.addInterceptor(interceptor)
    }.build()

    val instance: Retrofit by lazy {
        val gson: Gson = GsonBuilder().setLenient().create()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}
