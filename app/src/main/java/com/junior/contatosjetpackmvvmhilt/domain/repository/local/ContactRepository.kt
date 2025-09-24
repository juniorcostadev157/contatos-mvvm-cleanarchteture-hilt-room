package com.junior.contatosjetpackmvvmhilt.domain.repository.local

import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository  {

    suspend fun insertContact(contact: Contact)

    fun getAllContacts(): Flow<List<Contact>>

    suspend fun updateContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun getContactByPhone(phone: String): Contact?

    fun getContactById(id: Int): Flow<Contact?>
}