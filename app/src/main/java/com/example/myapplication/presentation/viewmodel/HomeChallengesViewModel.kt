package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.TokenManager
import com.example.myapplication.domain.model.InformationUser
import com.example.myapplication.domain.model.challenge_model.Challenge
import com.example.myapplication.domain.model.challenge_model.ChallengeRequest
import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.domain.model.challenge_model.ResponseData
import com.example.myapplication.domain.useCase.DeleteChallengeUseCase
import com.example.myapplication.domain.useCase.HomeChallengesUseCase
import com.example.myapplication.domain.useCase.InformationUserUseCase
import com.example.myapplication.utils.GlobalState
import com.example.myapplication.utils.StateGetChallenges
import com.example.persentation.domain.data.post_model.FilterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeChallengesViewModel @Inject constructor(
    private val homeChallengesUseCase: HomeChallengesUseCase,
):UpdateChallenge , ViewModel() {

    private val _challenges = MutableStateFlow<List<Challenge>>(emptyList())
    val challenges: StateFlow<List<Challenge>> = _challenges

    private val _totalPage = MutableStateFlow<Int>(1)
    val totalPage: StateFlow<Int> = _totalPage

    private val _counterPage = MutableStateFlow<Int>(1)
    val counterPage: StateFlow<Int> = _counterPage

    private val _initialState = MutableStateFlow<Boolean>(false)
    val initialState : StateFlow<Boolean> = _initialState

    private val _stateGetChallenges = MutableStateFlow<StateGetChallenges>(StateGetChallenges.Idle)
    val stateGetChallenges: StateFlow<StateGetChallenges> = _stateGetChallenges

    private val _stateFilter = MutableStateFlow<GlobalState>(GlobalState.IDLE)
    val stateFilter: StateFlow<GlobalState> = _stateFilter

    private val _totalPageFilter = MutableStateFlow<Int>(1)

    private val _challengesFilter = MutableStateFlow<List<Challenge>>(emptyList())
    val challengesFilter: StateFlow<List<Challenge>> = _challengesFilter

    private val _currentPageFilter = MutableStateFlow<Int>(1)
    val currentPageFilter: StateFlow<Int> = _currentPageFilter

    private val _isFilter = MutableStateFlow<Boolean>(false)
    val isFilter: StateFlow<Boolean> = _isFilter

    private val _initialFilterRequest = MutableStateFlow<FilterRequest?>(null)
    val initialFilterRequest: StateFlow<FilterRequest?> = _initialFilterRequest

    private val isGetFirstDataFilter = MutableStateFlow<Boolean>(true)

    init {
        totalPage()
        getAllChallenges()
        _initialState.value = true
    }

    fun getAllChallenges() {
        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 6")
        if (_totalPage.value==0) _stateGetChallenges.value = StateGetChallenges.NULL
        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 7   counterPage.value : ${counterPage.value},  totalPage.value : ${totalPage.value}")

        if (counterPage.value > totalPage.value) return

        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 8")

        viewModelScope.launch {
            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 9")

            _stateGetChallenges.value = StateGetChallenges.Loading
            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 10")

            Log.d("getAllChallenges", " Loading Challenges for page ${counterPage.value}")

            try {
                Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 11")

                val result = homeChallengesUseCase(page = counterPage.value)
                Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 12")


                when {
                    result.isSuccessful && result.body().isNullOrEmpty() && counterPage.value == 1 -> {
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 13")

                        _stateGetChallenges.value = StateGetChallenges.NULL
                        Log.d("getAllChallenges", " No posts found (page = 1)")
                    }

                    result.isSuccessful && result.body() != null -> {
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 14")

                        val newPosts = result.body()!!
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 15")

                        val uniquePosts = newPosts.filter { newItem ->
                            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 16")

                            _challenges.value.none { it.challengeID == newItem.challengeID }
                        }
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 17")

                        _challenges.value = _challenges.value + uniquePosts
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 18")

                        _stateGetChallenges.value = StateGetChallenges.Success(data = _challenges.value)
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 19")

                        _counterPage.value = counterPage.value + 1
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 20")

                        Log.d("getAllChallenges", " Success: loaded ${uniquePosts.size} unique Challenges")
                    }


                    result.isSuccessful && result.body() == null -> {
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 21")

                        _stateGetChallenges.value = StateGetChallenges.Failure(data = emptyList())
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 22")

                        Log.w("getAllChallenges", " Response successful but body is null")
                    }

                    else -> {
                        _stateGetChallenges.value = StateGetChallenges.Failure(data = emptyList())
                        Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 23")

                        Log.e("getAllChallenges", " Failed response: ${result.code()} - ${result.message()}")
                    }
                }

            } catch (e: Exception) {
                Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 24")

                _stateGetChallenges.value = StateGetChallenges.Failure(data = emptyList())
                Log.e("getAllChallenges", " Exception thrown: ${e.localizedMessage}", e)
            }
        }
    }

    fun restartCounterPage() {
        viewModelScope.launch {
            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 1")
            _stateGetChallenges.value = StateGetChallenges.Loading

            _challenges.value = emptyList()
            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 2")

            _counterPage.value = 1
            _totalPage.value = 1

            totalPage()
            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 3")

            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 4")

            getAllChallenges()

            Log.d("getAllChallenges", " FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF 5")

        }
    }


    fun totalPage(){

        viewModelScope.launch {
            try {

                val totalPageResponse = homeChallengesUseCase.totalPage()

                if (totalPageResponse.isSuccessful && totalPageResponse.body() != null && totalPageResponse.body()!! > 0) {
                    _totalPage.value = totalPageResponse.body()!!
                } else {
                    _totalPage.value = 1
                }


            }catch (e: Exception){
                _totalPage.value = 1
            }
        }
    }

    // Remove a challenge by its ID from the current list and update the state if the list becomes empty
    fun removeChallenge(challengeID: String) {
        _challenges.value = _challenges.value.filterNot { it.challengeID == challengeID }
        if (_challenges.value.isEmpty()) {
            _stateGetChallenges.value = StateGetChallenges.NULL
        }
    }

    override fun updateChallenge(challengeID: String, challengeRequest: ChallengeRequest): Boolean {
        var updated = false

        _challenges.update { challenges ->
            challenges.map { item ->
                if (item.challengeID == challengeID) {
                    updated = true
                    item.copy(
                        description = challengeRequest.descriptionPost,
                        club = challengeRequest.club,
                        whatsUpNumber = challengeRequest.whatsUpNumber,
                        gender = challengeRequest.gender,
                        team = challengeRequest.team,
                        governorate = challengeRequest.governorate,
                        city = challengeRequest.city,
                    )
                } else {
                    item // مهم ترجع العنصر كما هو إذا لم ينطبق الشرط
                }
            }
        }

        return updated
    }

    fun toggleFilter(isFilter : Boolean){
    }

    fun resetFilter(){
        viewModelScope.launch {
            _stateFilter.value = GlobalState.LOADING
            _currentPageFilter.value = 1
            _totalPageFilter.value = 1
            _challengesFilter.value = emptyList()
            filterChallenges()
        }
    }

    fun discordFilter(){
        viewModelScope.launch {
            _currentPageFilter.value = 1
            _totalPageFilter.value = 1
            _challengesFilter.value = emptyList()
            _isFilter.value = false
        }
    }

    fun setInitialFilter(filterRequest: FilterRequest){
        _isFilter.value = true
        _initialFilterRequest.value = filterRequest
        Log.d("Filter Challenge" , "${_initialFilterRequest.value}")

        resetFilter()
    }

    fun filterChallenges(){
        viewModelScope.launch {

            try {
                Log.d("Filter Challenge" , "Loading")
                if (_currentPageFilter.value > _totalPageFilter.value) {
                    _stateFilter.value = GlobalState.READY
                    return@launch
                }
                Log.d("Filter Challenge" , "Loading1")

                if (_stateFilter.value != GlobalState.LOADING)
                    _stateFilter.value = GlobalState.LOADING

                val result = homeChallengesUseCase.filterChallenge(page = currentPageFilter.value , filterRequest = _initialFilterRequest.value!!)
                Log.d("Filter Challenge" , "Loading2")


                if (result.isSuccessful && result.body() != null && result.body()!!.results.isNotEmpty()){

                    Log.d("Filter Challenge" , "Success")

                    if (!isGetFirstDataFilter.value){
                        isGetFirstDataFilter.value = false
                        _totalPageFilter.value = result.body()!!.totalPages
                    }

                    _challengesFilter.value = _challengesFilter.value + result.body()!!.results

                    Log.d("Filter Challenge" , "Success , ${_challengesFilter.value}")

                    _currentPageFilter.value = _currentPageFilter.value + 1

                    _stateFilter.value = GlobalState.READY

                }else{
                    Log.d("Filter Challenge" , "Empty")

                    _stateFilter.value = GlobalState.EMPTY
                }

            }catch (e: Exception){
                Log.d("Filter Challenge" , "Error")

                _stateFilter.value = GlobalState.Error
            }

        }
    }

}