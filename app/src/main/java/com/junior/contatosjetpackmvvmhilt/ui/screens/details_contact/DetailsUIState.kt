package com.junior.contatosjetpackmvvmhilt.ui.screens.details_contact

import com.junior.contatosjetpackmvvmhilt.data.model.Contact

data class DetailsUIState(
    val contact: Contact? = null,
    val isLoading: Boolean = true,
    val error: String? = null
)
