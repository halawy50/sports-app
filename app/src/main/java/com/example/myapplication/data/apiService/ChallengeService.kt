package com.example.myapplication.data.apiService

import com.example.myapplication.domain.model.PreviewDataUser
import com.example.myapplication.domain.model.post_model.Post
import com.example.myapplication.domain.model.post_model.PostRequest
import com.example.myapplication.domain.model.post_model.PostResponse
import com.example.myapplication.domain.model.post_model.PostsUserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {

    @POST("new_post")
    suspend fun newPost(
        @Header("Authorization") token: String,
        @Body postRequest: PostRequest
    ): Response<PostResponse>

    @GET("posts/{page}")
    suspend fun getPosts(
        @Path("page") page: Int
    ): Response<List<Post>>

    @GET("posts/total_page")
    suspend fun totalPage(): Response<Int>


    @GET("information_user/{user_id}")
    suspend fun previewDataUser(
        @Path("user_id") userId: String,
    ): Response<PreviewDataUser>



    @GET("posts_user/{user_id}/{page}")
    suspend fun getPostsUser(
        @Path("user_id") userId: String,
        @Path("page") page: Int
    ): Response<PostsUserResponse>


    @GET("posts/total_page/{user_id}")
    suspend fun totalPageUserPosts(
        @Path("user_id") userId: String,
    ): Response<Int>

}