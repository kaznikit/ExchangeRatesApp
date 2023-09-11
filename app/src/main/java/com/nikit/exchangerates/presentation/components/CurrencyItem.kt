package com.nikit.exchangerates.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nikit.exchangerates.R
import com.nikit.exchangerates.models.Currency

@Composable
fun CurrencyItem(model: Currency, onFavoriteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), text = model.name
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), text = model.course.toString()
        )
        IconButton(modifier = Modifier.size(40.dp), onClick = { onFavoriteClick() }) {
            Icon(
                painter = painterResource(id = if (model.isFavorite) R.drawable.ic_star_filled else R.drawable.ic_star_outlined),
                contentDescription = null
            )
        }
    }
}