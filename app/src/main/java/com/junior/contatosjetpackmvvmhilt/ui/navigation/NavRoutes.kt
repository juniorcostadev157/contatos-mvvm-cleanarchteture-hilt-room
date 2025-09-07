package com.junior.contatosjetpackmvvmhilt.ui.navigation

import com.junior.contatosjetpackmvvmhilt.core.contants.Constants


sealed class NavRoutes(val route: String){

    data object Home: NavRoutes(Constants.NavRoutes.HOME)

    data object AddContact: NavRoutes(Constants.NavRoutes.ADD_CONTACT)

    data object DetailsContact: NavRoutes(Constants.NavRoutes.DETAILS_CONTACT){
        fun createRoute(id: Int)= "$route/$id"
    }

    data object EditContact: NavRoutes(Constants.NavRoutes.EDIT_CONTACT){
        fun createRoute(id: Int) = "$route/$id"
    }


}