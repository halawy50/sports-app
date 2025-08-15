import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.myapplication.domain.model.EntryModel
import com.example.myapplication.presentation.constant.ChangeLanguage
import com.example.myapplication.ui.theme.white
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiSelectDropdown(
    items: List<EntryModel>,
    selectedItems: List<EntryModel>,
    onSelectionChanged: (List<EntryModel>) -> Unit,
    label: String = "",
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(items) {
        if (selectedItems.isEmpty() && items.isNotEmpty()) {
            onSelectionChanged(items)
        }
    }

    val allSelected = selectedItems.size == items.size && items.isNotEmpty()

    val displayText = when {
        selectedItems.isEmpty() -> stringResource(R.string.empty_select)
        allSelected -> stringResource(R.string.select_all)
        selectedItems.size == 1 ->if (ChangeLanguage.getSavedLanguage(context) == "ar")
            selectedItems.first().titleAr
        else
            selectedItems.first().titleEn
        else -> stringResource(R.string.selected_items_count, selectedItems.size)
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = it }
        ) {
            OutlinedTextField(
                value = displayText,
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                label = { Text(label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                readOnly = true,
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = if (expanded)
                                stringResource(R.string.close_menu)
                            else
                                stringResource(R.string.open_menu)
                        )
                    }
                },
                textStyle = LocalTextStyle.current.copy(),
            )


            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(color = white)
            ) {
                if (items.isNotEmpty()) {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = allSelected,
                                    onCheckedChange = {
                                        val newList = if (allSelected) emptyList() else items
                                        onSelectionChanged(newList)
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stringResource(R.string.select_all),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        },
                        onClick = {
                            val newList = if (allSelected) emptyList() else items
                            onSelectionChanged(newList)
                        }
                    )

                    HorizontalDivider()
                }

                items.forEach { item ->
                    val isSelected = selectedItems.contains(item)
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = {
                                        val newList = selectedItems.toMutableList().apply {
                                            if (isSelected) remove(item) else add(item)
                                        }
                                        onSelectionChanged(newList)
                                    }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = if (ChangeLanguage.getSavedLanguage(context) == "ar")
                                        item.titleAr
                                    else
                                        item.titleEn,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        },
                        onClick = {
                            val newList = selectedItems.toMutableList().apply {
                                if (isSelected) remove(item) else add(item)
                            }
                            onSelectionChanged(newList)
                        }
                    )
                }
            }
        }
    }
}


