package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.model.PreviewDataUser
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.useCase.PostsUserUseCase
import com.example.myapplication.utils.StateGetPosts
import com.example.myapplication.utils.StatePreviewDataUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostsUserViewModel @Inject constructor(
    private val postsUserUseCase: PostsUserUseCase,
    private val localTokenManager: TokenManager,

    ): ViewModel() {

    private val _posts = MutableStateFlow<List<Challenge>>(emptyList())
    val posts: StateFlow<List<Challenge>> = _posts

    private val _totalPage = MutableStateFlow<Int>(1)
    val totalPage: StateFlow<Int> = _totalPage

    private val _counterPage = MutableStateFlow<Int>(1)
    val counterPage: StateFlow<Int> = _counterPage


    private val _stateGetPosts = MutableStateFlow<StateGetPosts>(StateGetPosts.Idle)
    val stateGetPosts: StateFlow<StateGetPosts> = _stateGetPosts

    private val _statePreviewDataUser = MutableStateFlow<StatePreviewDataUser>(StatePreviewDataUser.Idle)
    val statePreviewDataUser: StateFlow<StatePreviewDataUser> = _statePreviewDataUser

    init {
        totalPage()
        getAllPostUser()
        previewDataUser()
    }


    fun previewDataUser(){
        viewModelScope.launch {
            _statePreviewDataUser.value = StatePreviewDataUser.Loading
            try {
                val result = postsUserUseCase.previewDataUser(localTokenManager.getUserId().toString())
                if (result.isSuccessful || result.body() != null){
                    _statePreviewDataUser.value = StatePreviewDataUser.Success(result.body()!!)
                    Log.d("Preview Data User" , result.body().toString())

                }else{
                    _statePreviewDataUser.value = StatePreviewDataUser.Failure(result.body()!!)
                }
            }catch (e: Exception){
                Log.d("Error Preview Data User" , e.toString())
                _statePreviewDataUser.value = StatePreviewDataUser.Failure(PreviewDataUser(
                    email = "",
                    gender = -1,
                    name = ""
                ))
            }
        }
    }


    fun getAllPostUser() {
        if (_stateGetPosts.value == StateGetPosts.Loading) return // 🔒 block retry

        if (counterPage.value > totalPage.value) return

        viewModelScope.launch {
            _stateGetPosts.value = StateGetPosts.Loading
            Log.d("getAllPost", " Loading posts for page ${counterPage.value}")

            try {
                val result = postsUserUseCase.invoke(page = counterPage.value, userId = localTokenManager.getUserId().toString())

                when {
                    result.isSuccessful && result.body()!!.data.isNullOrEmpty() && counterPage.value == 1 -> {
                        _stateGetPosts.value = StateGetPosts.NULL
                        Log.d("getAllPostUser", " No posts found (page = 1)")
                    }

                    result.isSuccessful && result.body() != null -> {
                        val newPosts = result.body()!!.data

                        _posts.value = _posts.value + newPosts

                        _stateGetPosts.value = StateGetPosts.Success(data = _posts.value)

                        _counterPage.value = counterPage.value + 1

                        Log.d("getAllPostUser", " Success: loaded ${newPosts.size} posts")
                    }

                    result.isSuccessful && result.body() == null -> {
                        _stateGetPosts.value = StateGetPosts.Failure(data = emptyList())
                        Log.w("getAllPostUser", " Response successful but body is null")
                    }

                    result.body()!!.statusCode==401 -> {
                        _stateGetPosts.value = StateGetPosts.Failure(data = emptyList())
                        Log.e("getAllPostUser", " Failed response: ${result.code()} - ${result.message()}")
                    }
                }

            } catch (e: Exception) {
                _stateGetPosts.value = StateGetPosts.Failure(data = emptyList())
                Log.e("getAllPostUser", " Exception thrown: ${e.localizedMessage}", e)
            }
        }
    }

    fun restartCounterPage() {
        _posts.value = emptyList()
        _counterPage.value = 1
        _stateGetPosts.value = StateGetPosts.Idle
    }


    fun totalPage(){

        viewModelScope.launch {
            try {

                val totalPageResponse = postsUserUseCase.totalPageUserPosts(userId = localTokenManager.getUserId().toString())

                if (totalPageResponse.isSuccessful && totalPageResponse.body() != null || totalPageResponse.body()!! > 0){
                    _totalPage.value = totalPageResponse.body() as Int
                }else{
                    _totalPage.value = 0
                }

            }catch (e: Exception){
                _totalPage.value = 0
            }
        }
    }
}