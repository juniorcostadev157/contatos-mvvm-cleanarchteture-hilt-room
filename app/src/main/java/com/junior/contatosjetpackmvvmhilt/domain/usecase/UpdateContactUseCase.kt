package com.junior.contatosjetpackmvvmhilt.domain.usecase

import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.domain.repository.local.ContactRepository
import javax.inject.Inject

class UpdateContactUseCase @Inject constructor(private val repository: ContactRepository) {

    suspend operator fun invoke(contact: Contact){
        repository.updateContact(contact)
    }
}