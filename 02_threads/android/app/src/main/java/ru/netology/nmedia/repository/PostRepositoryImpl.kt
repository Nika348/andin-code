package ru.netology.nmedia.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnkError
import java.io.IOException

private const val RESPONSE_CODE_SUCCESS = 200

class PostRepositoryImpl(private val postDao: PostDao) : PostRepository {
    override val data: LiveData<List<Post>> = postDao.getAll().map {
        it.map(PostEntity::toDto)
    }

    override suspend fun getAllAsync() {
        withContext(Dispatchers.IO) {
            try {
                val response = PostsApi.retrofitService.getAll()
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }

                response.body() ?: throw ApiError(response.code(), response.message())
                postDao.insert(response.body()!!.map { PostEntity.fromDto(it) })
                Log.d("PostRepositoryImpl", "${response.body()}")
            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                Log.e("PostRepositoryImpl", "error: $e")
                throw UnkError

            }
        }
    }

    override suspend fun saveAsync(post: Post) {
        withContext(Dispatchers.IO) {
            try {
                val response = PostsApi.retrofitService.save(post)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }

                val body = response.body() ?: throw ApiError(response.code(), response.message())
                postDao.insert(PostEntity.fromDto(body))
            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                throw UnkError
            }
        }
    }

    override suspend fun removeByIdAsync(id: Long) {
        withContext(Dispatchers.IO) {
            postDao.removeById(id)
            try {
                val response = PostsApi.retrofitService.removeById(id)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                throw UnkError
            }
        }
    }
    override suspend fun likeByIdAsync(id: Long) {
        withContext(Dispatchers.IO) {
            postDao.likeById(id)
            try {
                val response = PostsApi.retrofitService.likeById(id)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                val body = response.body() ?: throw ApiError(response.code(), response.message())
                postDao.insert(PostEntity.fromDto(body))
            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                throw UnkError
            }
        }
    }

    override suspend fun unlikeByIdAsync(id: Long) {
        withContext(Dispatchers.IO) {
            postDao.likeById(id)
            try {
                val response = PostsApi.retrofitService.unlikeById(id)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                val body = response.body() ?: throw ApiError(response.code(), response.message())
                postDao.insert(PostEntity.fromDto(body))
            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                throw UnkError
            }
        }
    }
}

