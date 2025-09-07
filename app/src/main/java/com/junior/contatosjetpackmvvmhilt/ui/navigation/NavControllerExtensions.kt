package com.junior.contatosjetpackmvvmhilt.ui.navigation

import androidx.navigation.NavController


fun NavController.navigateToDetails(id: Int){
    this.navigate(NavRoutes.DetailsContact.createRoute(id))
}

fun NavController.navigateToEdit(id: Int){
    this.navigate(NavRoutes.EditContact.createRoute(id))
}

fun NavController.navigateHome(popUpToStart: Boolean = false){
    if (popUpToStart){
        this.navigate(NavRoutes.Home.route){
            popUpTo(NavRoutes.Home.route){inclusive = true}
        }
    }else{
        this.navigate(NavRoutes.Home.route)
    }

}

fun NavController.navigateToAddContact(){
    this.navigate(NavRoutes.AddContact.route)
}