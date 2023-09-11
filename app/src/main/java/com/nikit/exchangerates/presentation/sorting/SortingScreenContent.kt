package com.nikit.exchangerates.presentation.sorting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Checkbox
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nikit.domain.models.SortingMethod
import com.nikit.exchangerates.R
import com.nikit.exchangerates.presentation.components.TopBar

@Composable
fun SortingScreenContent(sortingScreenViewModel: SortingScreenViewModel = hiltViewModel()) {
    val state by sortingScreenViewModel.state.collectAsState()

    Scaffold(topBar = {
        TopBar(
            title = stringResource(id = R.string.sorting_header_text),
            contentPadding = PaddingValues(4.dp)
        ) {
            sortingScreenViewModel.onAction(SortingScreenAction.OnBackClick)
        }
    }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp, horizontal = 4.dp)) {

            Text(text = stringResource(id = R.string.sorting_alphabet_text))

            Spacer(modifier = Modifier.height(8.dp))

            SortingCheckbox(
                text = stringResource(id = R.string.sorting_text),
                isChecked = state.byAlphabet == SortingMethod.DEFAULT
            ) {
                sortingScreenViewModel.onAction(SortingScreenAction.OnSortAlphabet(false))
            }

            Spacer(modifier = Modifier.height(8.dp))

            SortingCheckbox(
                text = stringResource(id = R.string.sorting_desc_text),
                isChecked = state.byAlphabet == SortingMethod.DESC
            ) {
                sortingScreenViewModel.onAction(SortingScreenAction.OnSortAlphabet(true))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = stringResource(id = R.string.sorting_alphabet_text))

            Spacer(modifier = Modifier.height(8.dp))

            SortingCheckbox(
                text = stringResource(id = R.string.sorting_text),
                isChecked = state.byValue == SortingMethod.DEFAULT
            ) {
                sortingScreenViewModel.onAction(SortingScreenAction.OnSortValue(false))
            }

            Spacer(modifier = Modifier.height(8.dp))

            SortingCheckbox(
                text = stringResource(id = R.string.sorting_desc_text),
                isChecked = state.byValue == SortingMethod.DESC
            ) {
                sortingScreenViewModel.onAction(SortingScreenAction.OnSortValue(true))
            }
        }
    }
}

@Composable
private fun SortingCheckbox(text: String, isChecked: Boolean, onCheck: (Boolean) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = isChecked, onCheckedChange = {
            onCheck(it)
        })

        Spacer(modifier = Modifier.width(8.dp))

        Text(text = text)
    }
}