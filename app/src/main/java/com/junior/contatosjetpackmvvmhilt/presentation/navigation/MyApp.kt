package com.junior.contatosjetpackmvvmhilt.presentation.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable

@Composable
fun MyApp(){
    MaterialTheme {
        Surface {
            AppNavHost()
        }
    }
}