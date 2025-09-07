package com.junior.contatosjetpackmvvmhilt.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.AppDataBase
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.dao.ContactDao
import com.junior.contatosjetpackmvvmhilt.data.datasource.local.ContactEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class AppDatabaseTest {

    private lateinit var db: AppDataBase
    private lateinit var contactDao: ContactDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        contactDao = db.contactDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testDatabaseInstanceNotNull() {
        assertNotNull(db)
    }

    @Test
    fun testDaoAccess() {
        assertNotNull(contactDao)
    }

    @Test
    fun testInsertMultipleContacts_andQueryAll() = runTest {
        val contacts = listOf(
            ContactEntity(1, "John Doe", "1234567890", "123 Main St", "12345", "Cidade", "Bairro", "Estado", "Rua"),
            ContactEntity(2, "Jane Smith", "9876543210", "456 Elm St", "654321", "Cidade", "Bairro", "Estado", "Rua"),
            ContactEntity(3, "Bob Johnson", "5555555555", "789 Oak St", "987654", "Cidade", "Bairro", "Estado", "Rua")
        )

        contacts.forEach { contactDao.insert(it) }

        val queriedContacts = contactDao.getAllContacts().first()
        assertEquals(contacts.size, queriedContacts.size)
        assertEquals(contacts[0].name, queriedContacts[0].name)
    }
}
