package com.example.myapplication.data.apiService

import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.model.challenge_model.ChallengeResponsePage
import com.example.myapplication.domain.model.challenge_model.SingleChallenge
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

    @PUT("update_challenge/{postId}")
    suspend fun updateChallenge(
        @Path("postId") postId: String,
        @Header("Authorization") accessToken: String,
        @Body challengeRequest: ChallengeRequest
    ): Response<ResponseData>

    @DELETE("delete_challenge/{postID}")
    suspend fun deleteChallenge(
        @Header("Authorization") accessToken: String,
        @Path("postID") challengeID: String
    ): Response<ResponseData>

    @GET("challenges/{page}")
    suspend fun getAllChallenges(
        @Path("page") page: Int
    ): Response<ChallengeResponsePage>


    @GET("challenge/{postId}")
    suspend fun getSingleChallenge(
        @Path("postId") postId: String
    ): Response<SingleChallenge>



    @GET("challenges_user/{user_id}/{page}")
    suspend fun getChallengesUser(
        @Path("user_id") userId: String,
        @Path("page") page: Int
    ): Response<ChallengeResponsePage>


    @POST("filter_challenges/{page}")
    suspend fun filterChallenge(
        @Path("page") page: Int,
        @Body filterRequest: FilterRequest
    ): Response<ChallengeResponsePage>

}