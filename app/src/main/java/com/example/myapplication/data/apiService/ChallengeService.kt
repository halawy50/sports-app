package com.example.myapplication.data.apiService

import com.example.myapplication.domain.model.PreviewDataUser
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.model.challenge_model.ChallengeUserResponse
import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.persentation.domain.data.post_model.FilterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT

interface ChallengeService {

    @POST("new_challenge")
    suspend fun addNewChallenge(
        @Header("Authorization") accessToken: String,
        @Body challengeRequest: ChallengeRequest
    ): Response<ResponseData>

    @DELETE("delete_challenge/{postID}")
    suspend fun deleteChallenge(
        @Header("Authorization") accessToken: String,
        @Path("postID") challengeID: String
    ): Response<ResponseData>

    @GET("challenges/{page}")
    suspend fun getChallenge(
        @Path("page") page: Int
    ): Response<List<Challenge>>

    @PUT("update_challenge/{postId}")
    suspend fun updateChallenge(
        @Path("postId") postId: String,
        @Header("Authorization") accessToken: String,
        @Body challengeRequest: ChallengeRequest
    ): Response<ResponseData>

    @GET("challenges/total_page")
    suspend fun totalPage(): Response<Int>

    @GET("challenge/{postId}")
    suspend fun getSingleChallenge(
        @Path("postId") postId: String
    ): Response<Challenge>


    @GET("information_user/{user_id}")
    suspend fun previewDataUser(
        @Path("user_id") userId: String,
    ): Response<PreviewDataUser>


    @GET("challenges_user/{user_id}/{page}")
    suspend fun getChallengesUser(
        @Path("user_id") userId: String,
        @Path("page") page: Int
    ): Response<ChallengeUserResponse>


    @GET("challenges/total_page/{user_id}")
    suspend fun totalPageUserChallenges(
        @Path("user_id") userId: String,
    ): Response<Int>

    @POST("filter_challenges/{page}")
    suspend fun filterChallenge(
        @Path("page") page: Int,
        @Body filterRequest: FilterRequest
    ): Response<FilterResponse>

}