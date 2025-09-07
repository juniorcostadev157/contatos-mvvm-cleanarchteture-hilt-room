package com.junior.contatosjetpackmvvmhilt.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.AppDataBase
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.dao.ContactDao
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.ContactEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ContactDaoTest {

    private lateinit var db: AppDataBase
    private lateinit var dao: ContactDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.contactDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertAndGetContactByPhone() = runTest {
        val contact = ContactEntity(1, "Junior", "123456", "123", "123", "Cidade", "Bairro", "Estado", "Rua")
        dao.insert(contact)

        val result = dao.getContactByPhone("123456")
        assertNotNull(result)
        assertEquals("Junior", result?.name)
    }

    @Test
    fun deleteContact_removesFromDb() = runTest {
        val contact = ContactEntity(2, "Maria", "654321", "321", "321", "Cidade", "Bairro", "Estado", "Rua")
        dao.insert(contact)
        dao.delete(contact)

        val result = dao.getContactByPhone("654321")
        assertNull(result)
    }

    @Test
    fun updateContact_changesData() = runTest {
        val contact = ContactEntity(3, "Pedro", "111222", "111", "111", "Cidade", "Bairro", "Estado", "Rua")
        dao.insert(contact)

        val updatedContact = contact.copy(name = "Pedro Atualizado")
        dao.update(updatedContact)

        val result = dao.getContactByPhone("111222")
        assertNotNull(result)
        assertEquals("Pedro Atualizado", result?.name)
    }

    @Test
    fun getAllContacts_returnsEmptyInitially() = runTest {
        val contacts = dao.getAllContacts().first()
        assertTrue(contacts.isEmpty())
    }

    @Test
    fun insertDuplicateContact_shouldFail() = runTest {
        val contact = ContactEntity(
            id = 4,
            name = "Ana",
            phone = "999999",
            cep = "999",
            logradouro = "Rua",
            bairro = "Bairro",
            complemento = "Complemento",
            estado = "Estado",
            localidade = "Cidade"
        )
        dao.insert(contact)

        val duplicate = contact.copy(name = "Ana Duplicada")

        // Room vai lançar SQLiteConstraintException ou algo similar
        val exception = kotlin.runCatching {
            dao.insert(duplicate)
        }.exceptionOrNull()

        assertNotNull(exception) // confirma que deu erro
        println("Erro esperado ao tentar duplicar: ${exception?.message}")
    }

}
