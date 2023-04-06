package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.netology.nmedia.api.ApiPosts
import ru.netology.nmedia.dto.Post
import java.util.concurrent.TimeUnit

private const val RESPONSE_CODE_SUCCESS = 200

class PostRepositoryImpl : PostRepository {
    private val client = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}
    private val typeTokenPost = object : TypeToken<Post>() {}

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
        private val jsonType = "application/json".toMediaType()
    }

    override fun getAllAsync(callback: PostRepository.Callback<List<Post>>) {
        ApiPosts.retrofitService.getAll().enqueue(object : retrofit2.Callback<List<Post>> {
            override fun onResponse(
                call: retrofit2.Call<List<Post>>,
                response: retrofit2.Response<List<Post>>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(Exception(response.message()))
                } else {
                    callback.onSuccess(requireNotNull(response.body()) { "body is null" })
                }
            }

            override fun onFailure(call: retrofit2.Call<List<Post>>, t: Throwable) {
                callback.onError(Exception(t))
            }


        })
    }

    override fun likeByIdAsync(id: Long, callback: PostRepository.Callback<Post>) {
        ApiPosts.retrofitService.likeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(Exception(response.message()))
                } else {
                    callback.onSuccess(requireNotNull(response.body()) { "body is null" })

                }
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }


        })
    }

    override fun unlikeByIdAsync(id: Long, callback: PostRepository.Callback<Post>){
        ApiPosts.retrofitService.unlikeById(id).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(Exception(response.message()))
                } else {
                    callback.onSuccess(requireNotNull(response.body()) { "body is null" })

                }
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }


        })
    }


    override fun saveAsync(post: Post, callback: PostRepository.Callback<Post>){
        ApiPosts.retrofitService.save(post).enqueue(object : retrofit2.Callback<Post> {
            override fun onResponse(
                call: retrofit2.Call<Post>,
                response: retrofit2.Response<Post>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(Exception(response.message()))
                } else {
                    callback.onSuccess(requireNotNull(response.body()) { "body is null" })

                }
            }

            override fun onFailure(call: retrofit2.Call<Post>, t: Throwable) {
                callback.onError(Exception(t))
            }


        })
    }
    override fun removeByIdAsync(id: Long, callback: PostRepository.Callback<Unit>){
        ApiPosts.retrofitService.removeById(id).enqueue(object : retrofit2.Callback<Unit> {
            override fun onResponse(
                call: retrofit2.Call<Unit>,
                response: retrofit2.Response<Unit>
            ) {
                if (!response.isSuccessful) {
                    callback.onError(Exception(response.message()))
                }
                if (response.code() == RESPONSE_CODE_SUCCESS) {
                    callback.onSuccess(Unit)
                }
            }

            override fun onFailure(call: retrofit2.Call<Unit>, t: Throwable) {
                callback.onError(Exception(t))
            }


        })
    }
}
