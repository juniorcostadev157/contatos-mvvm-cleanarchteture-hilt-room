package com.junior.contatosjetpackmvvmhilt.ui.screens.add_contact

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import com.junior.contatosjetpackmvvmhilt.core.contants.Constants
import com.junior.contatosjetpackmvvmhilt.core.utils.PhoneVisualTransformation
import com.junior.contatosjetpackmvvmhilt.ui.theme.NavyBlue
import com.junior.contatosjetpackmvvmhilt.ui.theme.White

@Composable
fun AddContactScreen(onBackClick: () -> Unit, viewModel: AddContactViewModel = hiltViewModel()){

    val name by viewModel.name.collectAsState()
    val phone by viewModel.phone.collectAsState()
    val complemento by viewModel.complemento.collectAsState()
    val cep by viewModel.cep.collectAsState()
    val logradouro by viewModel.logradouro.collectAsState()
    val bairro by viewModel.bairro.collectAsState()
    val localidade by viewModel.localidade.collectAsState()
    val estado by viewModel.estado.collectAsState()

    val eventFlow = viewModel.eventFlow
    val context = LocalContext.current

    LaunchedEffect(true) {
        eventFlow.collect { event->
           when(event){
               Constants.MESSAGE.MESSAGE_SUCCESS_INSERT -> {
                   Toast.makeText(context, event, Toast.LENGTH_SHORT).show()
                   onBackClick()
               }

               Constants.MESSAGE.MESSAGE_EMPTY_FIELDS->{
                   Toast.makeText(context, event, Toast.LENGTH_SHORT).show()
               }

               Constants.MESSAGE.MESSAGE_EXISTS_CONTACT->{
                   Toast.makeText(context, event, Toast.LENGTH_SHORT).show()
               }

           }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = name,
            onValueChange = {viewModel.setName(it)},
            label = { Text(text = Constants.Text.NAME)},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = NavyBlue,
                unfocusedIndicatorColor = NavyBlue,
                cursorColor = NavyBlue,
                focusedLabelColor = NavyBlue,
                unfocusedLabelColor = NavyBlue
        )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = phone,
            onValueChange = {input->
                val onlyDigits = input.filter { it.isDigit() }
                viewModel.setPhone(onlyDigits)},
            label = {Text(Constants.Text.PHONE)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            visualTransformation = PhoneVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = NavyBlue,
                unfocusedIndicatorColor = NavyBlue,
                cursorColor = NavyBlue,
                focusedLabelColor = NavyBlue,
                unfocusedLabelColor = NavyBlue
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = cep
            ,
            onValueChange = {viewModel.setCep(it)},
            label = {Text(Constants.Text.CEP)},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = NavyBlue,
                unfocusedIndicatorColor = NavyBlue,
                cursorColor = NavyBlue,
                focusedLabelColor = NavyBlue,
                unfocusedLabelColor = NavyBlue
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = logradouro
            ,
            onValueChange = {},
            label = {Text(Constants.Text.LOGRADOURO)},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = NavyBlue,
                unfocusedIndicatorColor = NavyBlue,
                cursorColor = NavyBlue,
                focusedLabelColor = NavyBlue,
                unfocusedLabelColor = NavyBlue
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = complemento,
            onValueChange = {viewModel.setComplemento(it)},
            label = {Text(Constants.Text.COMPLEMENTO)},
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = NavyBlue,
                unfocusedIndicatorColor = NavyBlue,
                cursorColor = NavyBlue,
                focusedLabelColor = NavyBlue,
                unfocusedLabelColor = NavyBlue
            )

        )

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = bairro
            ,
            onValueChange = {},
            label = {Text(Constants.Text.BAIRRO)},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = NavyBlue,
                unfocusedIndicatorColor = NavyBlue,
                cursorColor = NavyBlue,
                focusedLabelColor = NavyBlue,
                unfocusedLabelColor = NavyBlue
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = localidade
            ,
            onValueChange = {},
            label = {Text(Constants.Text.LOCALIDADE)},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = NavyBlue,
                unfocusedIndicatorColor = NavyBlue,
                cursorColor = NavyBlue,
                focusedLabelColor = NavyBlue,
                unfocusedLabelColor = NavyBlue
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = estado
            ,
            onValueChange = {},
            label = {Text(Constants.Text.ESTADO)},
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = NavyBlue,
                unfocusedTextColor = NavyBlue,
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = NavyBlue,
                unfocusedIndicatorColor = NavyBlue,
                cursorColor = NavyBlue,
                focusedLabelColor = NavyBlue,
                unfocusedLabelColor = NavyBlue
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val contact = Contact(
                name = name,
                phone = phone,
                complemento = complemento,
                cep = cep,
                logradouro = logradouro,
                bairro = bairro,
                localidade = localidade,
                estado= estado
            )
            viewModel.insertContact(contact)

        }, colors = ButtonDefaults.buttonColors(containerColor = NavyBlue)) {Text("Salvar", color = White) }


        }

    }
