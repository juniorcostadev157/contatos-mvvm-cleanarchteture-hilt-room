package com.junior.contatosjetpackmvvmhilt.data.repository.local


import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import kotlinx.coroutines.flow.Flow


interface ContactRepository  {

    suspend fun insertContact(contact: Contact)

    fun getAllContacts():Flow<List<Contact>>

    suspend fun updateContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)

    suspend fun getContactByPhone(phone: String): Contact?

    fun getContactById(id: Int): Flow<Contact?>
}
