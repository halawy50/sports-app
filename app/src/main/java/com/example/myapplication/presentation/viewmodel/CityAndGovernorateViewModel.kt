package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.City
import com.example.myapplication.domain.model.Governorate
import com.example.myapplication.domain.useCase.CityAndGovernorateUseCase
import com.example.myapplication.utils.StateCities
import com.example.myapplication.utils.StateGovernorate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityAndGovernorateViewModel @Inject constructor(private val useCase: CityAndGovernorateUseCase): ViewModel() {
    private val _stateGovernorate = MutableStateFlow<StateGovernorate>(StateGovernorate.Idle)
    val stateGovernorate: StateFlow<StateGovernorate> = _stateGovernorate

    private val _stateCities = MutableStateFlow<StateCities>(StateCities.Idle)
    val stateCities: StateFlow<StateCities> = _stateCities


    private val _governorate = MutableStateFlow<List<Governorate>>(emptyList())
    val governorate: StateFlow<List<Governorate>> = _governorate

    private val _cities = MutableStateFlow<List<City>>(emptyList())
    val cities: StateFlow<List<City>> = _cities



    init {
        getGovernorate()
    }

    fun setGovernorate(governorateList: List<Governorate>){
        _governorate.value = governorateList
    }

    fun setCity(cityList: List<City>){
        _cities.value = cityList
    }

    fun getGovernorate() {
        viewModelScope.launch {
            try {
                _stateGovernorate.value = StateGovernorate.Loading
                Log.d("DataGov", "Start fetching governorates")

                val result = useCase.getGovernorate()
                if (result.isSuccessful && result.body() != null) {
                    _stateGovernorate.value = StateGovernorate.Success(data = result.body()!!)
                    Log.d("DataGov", "Success: ${result.body()}")
                } else {
                    _stateGovernorate.value = StateGovernorate.Failure(data = emptyList())
                    Log.e("DataGov", "Failure: ${result.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _stateGovernorate.value = StateGovernorate.Failure(data = emptyList())
                Log.e("DataGov", "Exception: ${e.message}", e)
            }
        }
    }

    fun getCity(governorateId: String) {
        viewModelScope.launch {
            try {
                _stateCities.value = StateCities.Loading

                val result = useCase.getCities(governorateId = governorateId)
                if (result.isSuccessful && result.body() != null) {
                    _stateCities.value = StateCities.Success(data = result.body()!!)
                    Log.d("DataGovCity", "Success: ${result.body()}")
                } else {
                    _stateCities.value = StateCities.Failure(data = emptyList())
                    Log.e("DataGovCity", "Failure: ${result.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _stateCities.value = StateCities.Failure(data = emptyList())
                Log.e("DataGovCity", "Exception: ${e.message}", e)
            }
        }
    }


}