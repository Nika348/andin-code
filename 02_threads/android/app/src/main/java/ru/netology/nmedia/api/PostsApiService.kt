package ru.netology.nmedia.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.*
import ru.netology.nmedia.BuildConfig
import ru.netology.nmedia.dto.Post

private const val BASE_URL = "${BuildConfig.BASE_URL}/api/slow/"

interface PostsApiService {

    @GET("posts")
    fun getAll(): Call<List<Post>>

    @GET("posts/{id}")
    fun getById(@Path("id")id: Long): Call<Post>

    @POST("posts")
    fun save(@Body post: Post): Call<Post>

    @DELETE("posts/{id}")
    fun removeById(@Path("id")id: Long): Call<Unit>

    @POST("posts/{id}/likes")
    fun likeById(@Path("id")id: Long): Call<Post>

    @DELETE("posts/{id}/likes")
    fun unlikeById(@Path("id")id: Long): Call<Post>
}

private val logging = HttpLoggingInterceptor().apply {
    if (BuildConfig.DEBUG) {
        level = HttpLoggingInterceptor.Level.BODY
    }
}
private val client = OkHttpClient.Builder().addInterceptor(logging).build()
private val retrofit = Retrofit
    .Builder()
    .addConverterFactory(
        GsonConverterFactory
            .create()
    ).client(client)
    .baseUrl(BASE_URL).build()

object ApiPosts {
    val retrofitService: PostsApiService by lazy {
        retrofit.create()
    }
}