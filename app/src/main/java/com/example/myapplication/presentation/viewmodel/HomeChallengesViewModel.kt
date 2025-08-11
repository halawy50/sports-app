package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.useCase.HomePostUseCase
import com.example.myapplication.utils.StateGetPosts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePostViewModel @Inject constructor(
    private val homePostUseCase: HomePostUseCase,
) : ViewModel() {

    private val _posts = MutableStateFlow<List<Challenge>>(emptyList())
    val posts: StateFlow<List<Challenge>> = _posts

    private val _totalPage = MutableStateFlow<Int>(1)
    val totalPage: StateFlow<Int> = _totalPage

    private val _counterPage = MutableStateFlow<Int>(1)
    val counterPage: StateFlow<Int> = _counterPage


    private val _stateGetPosts = MutableStateFlow<StateGetPosts>(StateGetPosts.Idle)
    val stateGetPosts: StateFlow<StateGetPosts> = _stateGetPosts


    init {
        totalPage()
        getAllPost()
    }

    fun getAllPost() {
        if (_stateGetPosts.value == StateGetPosts.Loading) return // 🔒 مانع التكرار

        if (counterPage.value > totalPage.value) return

        viewModelScope.launch {
            _stateGetPosts.value = StateGetPosts.Loading
            Log.d("getAllPost", " Loading posts for page ${counterPage.value}")

            try {
                val result = homePostUseCase.invoke(page = counterPage.value)

                when {
                    result.isSuccessful && result.body().isNullOrEmpty() && counterPage.value == 1 -> {
                        _stateGetPosts.value = StateGetPosts.NULL
                        Log.d("getAllPost", " No posts found (page = 1)")
                    }

                    result.isSuccessful && result.body() != null -> {
                        val newPosts = result.body()!!

                        _posts.value = _posts.value + newPosts

                        _stateGetPosts.value = StateGetPosts.Success(data = _posts.value)

                        _counterPage.value = counterPage.value + 1

                        Log.d("getAllPost", " Success: loaded ${newPosts.size} posts")
                    }

                    result.isSuccessful && result.body() == null -> {
                        _stateGetPosts.value = StateGetPosts.Failure(data = emptyList())
                        Log.w("getAllPost", " Response successful but body is null")
                    }

                    else -> {
                        _stateGetPosts.value = StateGetPosts.Failure(data = emptyList())
                        Log.e("getAllPost", " Failed response: ${result.code()} - ${result.message()}")
                    }
                }

            } catch (e: Exception) {
                _stateGetPosts.value = StateGetPosts.Failure(data = emptyList())
                Log.e("getAllPost", " Exception thrown: ${e.localizedMessage}", e)
            }
        }
    }

    fun restartCounterPage() {
        _posts.value = emptyList()
        _counterPage.value = 1
        _stateGetPosts.value = StateGetPosts.Idle //  إعادة الحالة للوضع الافتراضي
    }


    fun totalPage(){

        viewModelScope.launch {
            try {

                val totalPageResponse = homePostUseCase.totalPage()

                if (totalPageResponse.isSuccessful && totalPageResponse.body() != null || totalPageResponse.body()!! > 0){
                    _totalPage.value = totalPageResponse.body() as Int
                }else{
                    _totalPage.value = 1
                }

            }catch (e: Exception){
                _totalPage.value = 1
            }
        }
    }
}