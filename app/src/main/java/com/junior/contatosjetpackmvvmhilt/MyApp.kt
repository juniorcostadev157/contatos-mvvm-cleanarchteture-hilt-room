package com.junior.contatosjetpackmvvmhilt

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.junior.contatosjetpackmvvmhilt.ui.navigation.AppNavHost

@Composable
fun MyApp(){
    MaterialTheme {
        Surface {
            AppNavHost()
        }
    }
}