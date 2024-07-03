package com.rayadev.jetpackcomposedisney

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.rayadev.jetpackcomposedisney.presentation.ui.theme.JetpackComposeDisneyTheme


@Composable
fun JetpackComposeDisneyApp(content: @Composable () -> Unit) {
    JetpackComposeDisneyTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}