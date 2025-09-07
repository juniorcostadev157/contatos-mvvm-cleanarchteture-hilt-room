package com.junior.contatosjetpackmvvmhilt.ui.screens.home

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.junior.contatosjetpackmvvmhilt.core.utils.PhoneVisualTransformation
import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import com.junior.contatosjetpackmvvmhilt.ui.theme.NavyBlue
import com.junior.contatosjetpackmvvmhilt.ui.theme.White
import androidx.core.net.toUri

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    onAddContactClick: () -> Unit,
    onContactClick: (Int)-> Unit,
    viewModel: HomeViewModel){

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddContactClick, containerColor = NavyBlue, contentColor = White) {
                Icon(imageVector = Icons.Default.Add,contentDescription = "Add Contact")
            }
        }
    ){
        when{
        uiState.isLoading->{
            Column (
                modifier = Modifier.fillMaxSize()
                    .padding(it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
               CircularProgressIndicator(color = NavyBlue)
            }
        }
            uiState.error != null ->{
                Text(text = uiState.error!!,
                    modifier = Modifier.padding(it))
            }

            uiState.contacts.isEmpty()->{
                Column(modifier = Modifier.fillMaxSize().padding(it),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    Text(text = "Nenhum contato encontrado")

                }
            }

            else->{

                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(it).padding(8.dp)
                ) {
                    items(uiState.contacts){contact->
                        ContactItem(contact, onClick = {onContactClick(contact.id)})
                    }
                }
            }


    }
    }


}

@Composable
fun ContactItem(contact: Contact, onClick: () -> Unit ){
    val context = LocalContext.current
    val phoneFormatter = PhoneVisualTransformation().formatPhoneNumber(contact.phone)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = NavyBlue,
            contentColor = White
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ){


        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){

            Column {
                Text(text = contact.name, style = MaterialTheme.typography.titleMedium)
                Text(text = phoneFormatter, style = MaterialTheme.typography.bodyMedium)
            }


            IconButton(onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = "tel:${contact.phone}".toUri()
                }
                context.startActivity(intent)
            }) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Ligar",
                    tint = White,

                )
            }
        }
    }
}


