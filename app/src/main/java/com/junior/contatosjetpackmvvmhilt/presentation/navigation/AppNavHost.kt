package com.junior.contatosjetpackmvvmhilt.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.junior.contatosjetpackmvvmhilt.presentation.components.topbar.TopBar
import com.junior.contatosjetpackmvvmhilt.presentation.screens.add_contact.AddContactScreen
import com.junior.contatosjetpackmvvmhilt.presentation.screens.details_contact.DetailsContactScreen
import com.junior.contatosjetpackmvvmhilt.presentation.screens.home.HomeScreen
import com.junior.contatosjetpackmvvmhilt.presentation.screens.home.HomeViewModel
import com.junior.contatosjetpackmvvmhilt.presentation.screens.update_contact.UpdateContactScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val  currentRoute = navBackStackEntry?.destination?.route
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState by homeViewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            TopBar(
                currentRoute = currentRoute,
                onBackClick = { navController.popBackStack() },
                isSearching = uiState.isSearching,
                searchQuery = uiState.query,
                onSearchClick = {homeViewModel.startSearch()},
                onQueryChange = {homeViewModel.onQueryChange(it)},
                onCloseSearch = {homeViewModel.closeSearch()}
            )
        }
    ) {paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavRoutes.Home.route,
            modifier = Modifier.padding(paddingValues)
        ){
            composable(NavRoutes.Home.route){
                HomeScreen (onAddContactClick =  {navController.navigateToAddContact()},
                    onContactClick = {contactId->
                        navController.navigateToDetails(contactId)

            }, viewModel = homeViewModel
                )
            }

            composable(NavRoutes.AddContact.route){
                AddContactScreen(
                    onBackClick = {navController.popBackStack()}
                )
            }

            composable(route="${NavRoutes.DetailsContact.route}/{id}",
               arguments = listOf(navArgument("id"){type = NavType.IntType})) {
                val id = it.arguments?.getInt("id")
                if (id != null) {
                    DetailsContactScreen(
                        contactId = id,

                        onEditClick = { navController.navigateToEdit(id) },
                        onDeleteSuccess = {
                                  navController.navigateHome(popUpToStart = true)
                            }
                    )
                        }

                }


            composable(route = "${NavRoutes.EditContact.route}/{id}",
                arguments = listOf(navArgument("id"){type = NavType.IntType})){
                val id = it.arguments?.getInt("id")
                if (id != null){
                    UpdateContactScreen(
                        contactId = id,
                        onUpdateSuccess = {navController.navigateHome(popUpToStart = true)}

                    )
                }
            }

    }
}
}