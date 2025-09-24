package com.junior.contatosjetpackmvvmhilt.domain.usecase

import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.domain.repository.local.ContactRepository
import jakarta.inject.Inject

class GetContactByPhoneUseCase @Inject constructor(private val repository: ContactRepository){

    suspend operator fun invoke(phone: String): Contact?{
        return repository.getContactByPhone(phone)
    }
}