package com.junior.contatosjetpackmvvmhilt.presentation.components.topbar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.junior.contatosjetpackmvvmhilt.R
import com.junior.contatosjetpackmvvmhilt.presentation.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    currentRoute: String?,
    onBackClick: () -> Unit,
    isSearching: Boolean,
    searchQuery: String,
    onSearchClick: ()-> Unit,
    onQueryChange: (String)-> Unit,
    onCloseSearch: ()-> Unit
){
    val showBackButton = currentRoute != NavRoutes.Home.route && !isSearching
    val isHome = currentRoute == NavRoutes.Home.route




    CenterAlignedTopAppBar(
        title = {
            if (isSearching){
                TextField(
                    value = searchQuery,
                    onValueChange = onQueryChange,
                    placeholder = {Text("Buscar contato ...")},
                    singleLine = true
                )
            }else{

                val title = when(currentRoute){
                    NavRoutes.Home.route -> "Meus Contatos"
                    NavRoutes.AddContact.route -> "Adicionar Contato"
                    NavRoutes.DetailsContact.route-> "Detalhes do Contato"
                    NavRoutes.EditContact.route-> "Editar Contato"
                    else -> "App Contatos"

                }
                Text(title)
            }
             } ,
        navigationIcon = {
            if (showBackButton){
                IconButton(onClick = onBackClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Voltar"
                    )
                }
            }
        }, actions = {
            if (isHome){
                if(isSearching){

                    IconButton(onClick = onCloseSearch) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_close),
                            contentDescription = "Fechar busca"
                        )
                    }
                }else{
                    IconButton(onClick = onSearchClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Buscar"
                        )
                    }
                }

            }
        }

        )
}