package com.junior.contatosjetpackmvvmhilt.ui.components.actionbottombar


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.junior.contatosjetpackmvvmhilt.ui.theme.NavyBlue


@Composable
fun ActionButtonBar(onEdit: () -> Unit, onDelete: () -> Unit, onMore: () -> Unit) {
    Column {
        HorizontalDivider(
            thickness = 1.dp,
            // linha separando o rodapé do conteúdo
            color = NavyBlue
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            IconButton(onClick = { onEdit ()}) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = { onDelete()}) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir")
            }
            IconButton(onClick = { /* TODO */ }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Mais")
            }
        }
    }
}

