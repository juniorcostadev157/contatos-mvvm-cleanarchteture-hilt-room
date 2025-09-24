package com.junior.contatosjetpackmvvmhilt.presentation.screens.details_contact

import com.junior.contatosjetpackmvvmhilt.domain.model.Contact

data class DetailsUIState(
    val contact: Contact? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
