package com.junior.contatosjetpackmvvmhilt.data.usecase

import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import com.junior.contatosjetpackmvvmhilt.data.repository.local.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactByIDUseCase @Inject constructor(private val repository: ContactRepository) {

    operator fun invoke(id: Int): Flow<Contact?>{
        return repository.getContactById(id)
    }

}