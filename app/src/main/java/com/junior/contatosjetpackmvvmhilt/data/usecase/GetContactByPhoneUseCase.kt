package com.junior.contatosjetpackmvvmhilt.data.usecase

import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import com.junior.contatosjetpackmvvmhilt.data.repository.local.ContactRepository
import jakarta.inject.Inject

class GetContactByPhoneUseCase @Inject constructor(private val repository: ContactRepository){

    suspend operator fun invoke(phone: String): Contact?{
        return repository.getContactByPhone(phone)
    }
}