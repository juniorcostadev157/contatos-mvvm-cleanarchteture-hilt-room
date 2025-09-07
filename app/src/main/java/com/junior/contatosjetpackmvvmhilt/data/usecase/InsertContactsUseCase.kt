package com.junior.contatosjetpackmvvmhilt.data.usecase

import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import com.junior.contatosjetpackmvvmhilt.data.repository.local.ContactRepository
import javax.inject.Inject

class InsertContactsUseCase @Inject constructor(private val repository: ContactRepository) {

    suspend operator fun invoke(contact: Contact){
        repository.insertContact(contact)
    }
}