package com.junior.contatosjetpackmvvmhilt.presentation.screens.home


import com.junior.contatosjetpackmvvmhilt.domain.model.Contact

data class HomeUIState(
    val contacts: List<Contact> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val allContacts: List<Contact> = emptyList(),
    val query: String = "",
    val isSearching: Boolean = false
)