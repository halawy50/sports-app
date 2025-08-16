package com.example.myapplication.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.EntryModel
import com.example.myapplication.domain.model.challenge_model.FilterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject



@HiltViewModel
class FilterViewModel @Inject constructor() : ViewModel() {


    private val _listEntryModelGovernorate = MutableStateFlow<List<EntryModel>>(emptyList())
    val listEntryModelGovernorate: StateFlow<List<EntryModel>> = _listEntryModelGovernorate.asStateFlow()

    private val _listEntryModelCities = MutableStateFlow<List<EntryModel>>(emptyList())
    val listEntryModelCities: StateFlow<List<EntryModel>> = _listEntryModelCities.asStateFlow()

    private val _selectedCities = MutableStateFlow<List<EntryModel>>(emptyList())
    val selectedCities: StateFlow<List<EntryModel>> = _selectedCities.asStateFlow()

    private val _selectedTeam = MutableStateFlow<List<EntryModel>>(emptyList())
    val selectedTeam: StateFlow<List<EntryModel>> = _selectedTeam.asStateFlow()

    private val _selectedGender = MutableStateFlow<List<EntryModel>>(emptyList())
    val selectedGender: StateFlow<List<EntryModel>> = _selectedGender.asStateFlow()

    private val _selectIdGovernorate = MutableStateFlow("")
    val selectIdGovernorate: StateFlow<String> = _selectIdGovernorate.asStateFlow()

    private val _selectIdOrder = MutableStateFlow(0)
    val selectIdOrder: StateFlow<Int> = _selectIdOrder.asStateFlow()

    fun resetToDefaultFilter() {
        _selectedCities.value = emptyList()
        _selectedGender.value = emptyList()
        _selectedTeam.value = emptyList()
        _selectIdGovernorate.value = ""
        _selectIdOrder.value = 0
    }


    fun setGovernorates(list: List<EntryModel>) {
        _listEntryModelGovernorate.value = list
    }

    fun setCities(list: List<EntryModel>) {
        _listEntryModelCities.value = list
    }

    fun setSelectedCities(list: List<EntryModel>) {
        _selectedCities.value = list
    }

    fun setSelectedTeam(list: List<EntryModel>) {
        _selectedTeam.value = list
    }

    fun setSelectedGender(list: List<EntryModel>) {
        _selectedGender.value = list
    }

    fun setGovernorateId(id: String) {
        _selectIdGovernorate.value = id
    }

    fun setOrderId(id: Int) {
        _selectIdOrder.value = id
    }

    fun buildFilterRequest(): FilterRequest {
        return FilterRequest(
            cityIdsList = _selectedCities.value.map { it.index.toString() },
            gendersIdsList = _selectedGender.value.map { it.index },
            teamList = _selectedTeam.value.map {
                val data = it.index + 1
                Log.d("FilterRequest", data.toString())
                data
            },
            sortOrder = _selectIdOrder.value == 0
        )
    }
}
