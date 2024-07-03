package com.rayadev.jetpackcomposedisney.presentation.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowOutward
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.rayadev.jetpackcomposedisney.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchHistoryItem(
    text: String,
    onDeleteClicked: () -> Unit,
    onItemSelected: (String) -> Unit,
    onIconSelected: (String) -> Unit,
    onLongClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemSelected(text) })
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .combinedClickable(
                onClick = { onItemSelected(text) },
                onLongClick = { onLongClick() }
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Replay,
            contentDescription = stringResource(R.string.history),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onDeleteClicked,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowOutward,
                contentDescription = stringResource(R.string.delete),
                tint = Color.Black,
                modifier = Modifier
                    .clickable(onClick = { onIconSelected(text) })
            )
        }
    }
}
