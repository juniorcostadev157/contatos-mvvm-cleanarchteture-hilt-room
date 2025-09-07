package com.junior.contatosjetpackmvvmhilt.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.junior.contatosjetpackmvvmhilt.ui.navigation.MyApp
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
