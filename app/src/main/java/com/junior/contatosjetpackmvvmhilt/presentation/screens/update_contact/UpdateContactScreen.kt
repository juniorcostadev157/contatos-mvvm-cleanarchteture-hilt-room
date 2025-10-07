package com.junior.contatosjetpackmvvmhilt.presentation.screens.update_contact

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import com.junior.contatosjetpackmvvmhilt.core.contants.Constants
import com.junior.contatosjetpackmvvmhilt.core.utils.PhoneVisualTransformation
import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.presentation.screens.details_contact.DetailsScreenViewModel
import com.junior.contatosjetpackmvvmhilt.presentation.theme.NavyBlue
import com.junior.contatosjetpackmvvmhilt.presentation.theme.White


@Composable
fun UpdateContactScreen(
    contactId: Int,
    viewModelDetails: DetailsScreenViewModel = hiltViewModel(),
    viewModelUpdate: UpdateContactViewModel = hiltViewModel(),
    onUpdateSuccess: () -> Unit
){

    val uiState by viewModelDetails.uiState.collectAsState()
    val nome by viewModelUpdate.nome.collectAsState()
    val phone by viewModelUpdate.phone.collectAsState()
    val cep by viewModelUpdate.cep.collectAsState()
    val complemento by viewModelUpdate.complemento.collectAsState()
    val bairro by viewModelUpdate.bairro.collectAsState()
    val estado by viewModelUpdate.estado.collectAsState()
    val logradouro by viewModelUpdate.logradouro.collectAsState()
    val localidade by viewModelUpdate.localidade.collectAsState()

    val eventFlow  = viewModelUpdate.eventFlow
    val context = LocalContext.current






    LaunchedEffect(contactId) {
        viewModelDetails.loadContact(contactId)
    }

    LaunchedEffect(uiState.contact) {
        uiState.contact?.let { contact ->
            viewModelUpdate.setNome(contact.name)
            viewModelUpdate.setPhone(contact.phone)
            viewModelUpdate.setCep(contact.cep)
            viewModelUpdate.setComplemento(contact.complemento)
            viewModelUpdate.setBairro(contact.bairro)
            viewModelUpdate.setLogradouro(contact.logradouro)
            viewModelUpdate.setLocalidade(contact.bairro)
            viewModelUpdate.setEstado(contact.estado)
        }
    }

    LaunchedEffect(true) {
        eventFlow.collect { event->
            when(event){
                Constants.Message.MESSAGE_SUCCESS_UPDATE -> {
                    Toast.makeText(context, event, Toast.LENGTH_SHORT).show()
                    onUpdateSuccess()
                }
                Constants.Message.MESSAGE_EMPTY_FIELDS->{
                    Toast.makeText(context, event, Toast.LENGTH_SHORT).show()

                }

                Constants.Message.MESSAGE_EXISTS_CONTACT->{
                    Toast.makeText(context, event, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    when{
        uiState.isLoading->{
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }

        uiState.error != null ->{
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text("Erro; ${uiState.error}")
            }
        }

        uiState.contact != null-> {

            Column(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                OutlinedTextField(
                    value = nome,
                    onValueChange = {viewModelUpdate.setNome(it)},
                    label = {Text(Constants.TextField.NAME)},
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
                        viewModelUpdate.setPhone(onlyDigits)
                                    },
                    label = {Text(Constants.TextField.PHONE)},
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    visualTransformation = PhoneVisualTransformation(),
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
                    value = cep,
                    onValueChange = {viewModelUpdate.setCep(it)},
                    label = {Text(Constants.TextField.CEP)},
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
                    onValueChange = {viewModelUpdate.setComplemento(it)},
                    label = { Text(Constants.TextField.COMPLEMENTO)},
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
                    value = logradouro,
                    onValueChange = {viewModelUpdate.setLogradouro(it)},
                    label = { Text(Constants.TextField.LOGRADOURO)},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
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
                    value = bairro,
                    onValueChange = {viewModelUpdate.setBairro(it)},
                    label = { Text(Constants.TextField.BAIRRO)},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
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

                OutlinedTextField(
                    value = localidade,
                    onValueChange = {viewModelUpdate.setLocalidade(it)},
                    label = { Text(Constants.TextField.LOCALIDADE)},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
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

                OutlinedTextField(
                    value = estado,
                    onValueChange = {viewModelUpdate.setEstado(it)},
                    label = { Text(Constants.TextField.ESTADO)},
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
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

                Button(onClick = {
                    val contact = Contact(
                        id = contactId,
                        name = nome,
                        phone = phone,
                        cep = cep,
                        complemento = complemento,
                        estado = estado,
                        bairro = bairro,
                        localidade = localidade,
                        logradouro = logradouro

                    )
                    viewModelUpdate.updateContact(contact)

                }, colors = ButtonDefaults.buttonColors(containerColor = NavyBlue)) {

                    Text("ATUALIZAR", color = White)
                }
            }

        }
    }



    }


