package com.junior.contatosjetpackmvvmhilt.ui.screens.details_contact



import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.junior.contatosjetpackmvvmhilt.core.contants.Constants
import com.junior.contatosjetpackmvvmhilt.core.utils.PhoneVisualTransformation
import com.junior.contatosjetpackmvvmhilt.ui.components.actionbottombar.ActionButtonBar
import com.junior.contatosjetpackmvvmhilt.ui.theme.Blue
import com.junior.contatosjetpackmvvmhilt.ui.theme.Gray
import com.junior.contatosjetpackmvvmhilt.ui.theme.NavyBlue
import com.junior.contatosjetpackmvvmhilt.ui.theme.Red
import com.junior.contatosjetpackmvvmhilt.ui.theme.White


@Composable
fun DetailsContactScreen(contactId: Int, onEditClick: () -> Unit, onDeleteSuccess: () -> Unit, viewModel: DetailsScreenViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    var showDialog  by remember { mutableStateOf(false) }
    val context = LocalContext.current



    LaunchedEffect(contactId) {
        viewModel.loadContact(contactId)
    }

    Scaffold(
        bottomBar = {
        if (uiState.contact != null){
            ActionButtonBar(onEdit = {onEditClick()}, onDelete = {

                showDialog = true

            }, onMore = {})
        }
    }) {paddingValues ->
        when{
            uiState.isLoading->{
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    CircularProgressIndicator()
                }
            }

            uiState.error !=null->{
                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center){
                    Text("Erro: ${uiState.error}")
                }
            }

            uiState.contact != null ->{

                Card( modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = NavyBlue,
                        contentColor = White
                    ),elevation = CardDefaults.cardElevation(4.dp)

                ) { val contact = uiState.contact!!
                    val phoneFormatter = PhoneVisualTransformation().formatPhoneNumber(contact.phone)
                    Column (modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally){
                        val text = Constants.Text
                        Text(text = "${text.NAME}: ${contact.name}")
                        Spacer(Modifier.height(8.dp))
                        Text(text = "${text.PHONE}: $phoneFormatter")
                        Spacer(Modifier.height(8.dp))
                        Text(text = "${text.CEP}: ${contact.cep}")
                        Spacer(Modifier.height(8.dp))
                        Text(text = "${text.COMPLEMENTO}: ${contact.complemento}")
                        Spacer(Modifier.height(8.dp))
                        Text(text = "${text.BAIRRO}: ${contact.bairro}")
                        Spacer(Modifier.height(8.dp))
                        Text(text = "${text.LOGRADOURO}: ${contact.logradouro}")
                        Spacer(Modifier.height(8.dp))
                        Text(text = "${text.ESTADO}: ${contact.estado}")
                        Spacer(Modifier.height(8.dp))
                        Text(text = "${text.LOCALIDADE}: ${contact.localidade}")

                    } }

            }
            else->{
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                    contentAlignment = Alignment.Center){
                    Text("Contato não encontrado")
                }

            }
        }


    }
    if (showDialog){
        DeleteConfirmationDialog(onConfirm = {
            uiState.contact?.let { viewModel.deleteContact(it) }
            showDialog = false
            onDeleteSuccess()
            Toast.makeText(context, Constants.MESSAGE.MESSAGE_SUCCESS_DELETE, Toast.LENGTH_SHORT).show()
        }, onDismiss = {showDialog = false})
    }


}
@Composable
fun DeleteConfirmationDialog( onConfirm: () -> Unit, onDismiss: () -> Unit){

        AlertDialog(
            onDismissRequest = {onDismiss},
            confirmButton = { TextButton(onClick = onConfirm){Text("Excluir", color = Red)} },
            dismissButton = {TextButton(onClick = onDismiss){Text("Cancelar", color = Gray)}},
            title =  {Text("Confirmação de exclusão", color = White)},
            text = {Text("Você tem certeza que deseja excluir este contato ?", color = White)},
            containerColor = Blue
        )
}



