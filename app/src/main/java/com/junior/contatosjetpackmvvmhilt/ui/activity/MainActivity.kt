package com.junior.contatosjetpackmvvmhilt.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.junior.contatosjetpackmvvmhilt.MyApp
import com.junior.contatosjetpackmvvmhilt.ui.theme.ContatosJetPackMVVMHiltTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContatosJetPackMVVMHiltTheme {
                MyApp()

                }
            }
        }
    }
