package com.example.myapplication.presentation.components

import MultiSelectDropdown
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.myapplication.domain.model.EntryModel
import com.example.myapplication.presentation.components.InputsComponents.DropDawnSelect
import com.example.myapplication.presentation.viewmodel.CityAndGovernorateViewModel
import com.example.myapplication.ui.theme.white
import com.example.myapplication.utils.StateCities
import com.example.myapplication.utils.StateGovernorate
import com.example.myapplication.R
import com.example.myapplication.domain.model.challenge_model.FilterRequest
import com.example.myapplication.presentation.components.ButtonsComponents.ButtonFill
import com.example.myapplication.presentation.constant.challengeTeamList
import com.example.myapplication.presentation.constant.genderList
import com.example.myapplication.presentation.constant.orderList
import com.example.myapplication.presentation.viewmodel.FilterViewModel
import com.example.myapplication.presentation.viewmodel.InformationUserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetComponent(
    filterViewModel: FilterViewModel,
    showBottomSheet: Boolean = false,
    onDismiss: () -> Unit,
    cityAndGovernorateViewModel: CityAndGovernorateViewModel,
    onClickFilter: (FilterRequest) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val listEntryModelGovernorate by filterViewModel.listEntryModelGovernorate.collectAsState()
    val listEntryModelCities by filterViewModel.listEntryModelCities.collectAsState()
    val selectedCities by filterViewModel.selectedCities.collectAsState()
    val selectedTeam by filterViewModel.selectedTeam.collectAsState()
    val selectedGender by filterViewModel.selectedGender.collectAsState()
    val selectIdGovernorate by filterViewModel.selectIdGovernorate.collectAsState()
    val selectIdOrder by filterViewModel.selectIdOrder.collectAsState()

    val stateGovernorate by cityAndGovernorateViewModel.stateGovernorate.collectAsState()
    val cityState by cityAndGovernorateViewModel.stateCities.collectAsState()

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = sheetState,
            containerColor = white,
            windowInsets = WindowInsets(0, 0, 0, 0),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(
                    min = 400.dp,
                    max = LocalConfiguration.current.screenHeightDp.dp * 1f
                ),
            dragHandle = {
                Surface(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f),
                    shape = MaterialTheme.shapes.extraLarge
                ) {
                    Box(Modifier.size(width = 32.dp, height = 4.dp))
                }
            }
        ) {
            Box(modifier = Modifier.fillMaxWidth().background(white)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 20.dp)
                        .padding(top = 8.dp, bottom = 120.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {

                    HeaderText(text = stringResource(R.string.filter))

                    // Order Section
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        DropDawnSelect(
                            list = orderList(),
                            selectFirst = true,
                            label = stringResource(R.string.order),
                            getSelected = { select ->
                                filterViewModel.setOrderId(select.index)
                            }
                        )
                    }

                    // Governorate Section
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        when (val state = stateGovernorate) {
                            StateGovernorate.Loading -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                                    Text(stringResource(R.string.loading))
                                }
                            }

                            is StateGovernorate.Success -> {
                                val governorates = state.data.map { entry ->
                                    EntryModel(
                                        index = entry.id.toInt(),
                                        titleAr = entry.governorateNameAr,
                                        titleEn = entry.governorateNameEn
                                    )
                                }
                                filterViewModel.setGovernorates(governorates)

                                DropDawnSelect(
                                    selectUseIndex = if (selectIdGovernorate.isNotEmpty()) selectIdGovernorate.toInt() else -1,
                                    list = governorates,
                                    label = stringResource(R.string.governorate_Select),
                                    getSelected = { select ->
                                        filterViewModel.setSelectedCities(emptyList())
                                        filterViewModel.setCities(emptyList())
                                        filterViewModel.setGovernorateId(select.index.toString())

                                        cityAndGovernorateViewModel.getCity(
                                            governorateId = select.index.toString()
                                        )
                                    }
                                )
                            }

                            is StateGovernorate.Failure -> Text(stringResource(R.string.something_wrong))
                            else -> {}
                        }
                    }

                    // City Section
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        when (val state = cityState) {
                            StateCities.Loading -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    CircularProgressIndicator(modifier = Modifier.size(20.dp))
                                    Text(stringResource(R.string.loading))
                                }
                            }

                            is StateCities.Success -> {
                                val cities = state.data.map { entry ->
                                    EntryModel(
                                        index = entry.id.toInt(),
                                        titleAr = entry.city_name_ar,
                                        titleEn = entry.city_name_en
                                    )
                                }
                                filterViewModel.setCities(cities)

                                MultiSelectDropdown(
                                    items = cities,
                                    selectedItems = selectedCities,
                                    onSelectionChanged = { filterViewModel.setSelectedCities(it) },
                                    label = stringResource(R.string.select_city_or_group)
                                )
                            }

                            is StateCities.Failure -> Text(stringResource(R.string.city_load_failed))
                            StateCities.Idle -> MultiSelectDropdown(
                                items = emptyList(),
                                selectedItems = selectedCities,
                                onSelectionChanged = { filterViewModel.setSelectedCities(it) },
                                label = stringResource(R.string.select_city_or_group)
                            )

                            else -> {}
                        }
                    }

                    // Team Section
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        MultiSelectDropdown(
                            items = challengeTeamList(),
                            selectedItems = selectedTeam,
                            onSelectionChanged = { filterViewModel.setSelectedTeam(it) },
                            label = stringResource(R.string.select_city_or_group)
                        )
                    }

                    // Gender Section
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        MultiSelectDropdown(
                            items = genderList(),
                            selectedItems = selectedGender,
                            onSelectionChanged = { filterViewModel.setSelectedGender(it) },
                            label = stringResource(R.string.select_gender)
                        )
                    }

                    // Button Section
                    ButtonFill(
                        onClick = { onClickFilter(filterViewModel.buildFilterRequest()) },
                        label = stringResource(R.string.filter)
                    )
                }
            }
        }
    }
}
