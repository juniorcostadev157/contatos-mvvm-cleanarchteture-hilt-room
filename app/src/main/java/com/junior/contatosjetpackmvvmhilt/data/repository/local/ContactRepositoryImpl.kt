package com.junior.contatosjetpackmvvmhilt.data.repository.local

import com.junior.contatosjetpackmvvmhilt.data.datasource.local.dao.ContactDao
import com.junior.contatosjetpackmvvmhilt.data.model.mapper.toDomain
import com.junior.contatosjetpackmvvmhilt.data.model.mapper.toEntity
import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(private val dao: ContactDao): ContactRepository {

    override suspend fun insertContact(contact: Contact) {
       dao.insert(contact.toEntity())
    }

    override fun getAllContacts(): Flow<List<Contact>> {
      return  dao.getAllContacts().map { list-> list.map { it.toDomain() } }
    }

    override suspend fun updateContact(contact: Contact) {
        dao.update(contact.toEntity())
    }

    override suspend fun deleteContact(contact: Contact) {
        dao.delete(contact.toEntity())
    }

    override suspend fun getContactByPhone(phone: String): Contact? {
       return dao.getContactByPhone(phone)?.toDomain()
    }

    override fun getContactById(id: Int): Flow<Contact?> {
        return dao.getContactById(id).map { it?.toDomain() }
    }
}