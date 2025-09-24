package com.junior.contatosjetpackmvvmhilt.domain.usecase

import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.domain.repository.local.ContactRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactByIDUseCase @Inject constructor(private val repository: ContactRepository) {

    operator fun invoke(id: Int): Flow<Contact?>{
        return repository.getContactById(id)
    }

}