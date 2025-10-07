import com.junior.contatosjetpackmvvmhilt.data.datasource.local.dao.ContactDao
import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.domain.model.toEntity
import com.junior.contatosjetpackmvvmhilt.data.repository.local.ContactRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class ContactRepositoryTest {

    private lateinit var dao: ContactDao
    private lateinit var repositoryImpl: ContactRepositoryImpl

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repositoryImpl = ContactRepositoryImpl(dao)
    }

    private fun makeContact(
        id: Int = 1,
        name: String = "Junior",
        phone: String = "1234567890",
        cep: String = "12345",
        logradouro: String = "123 Main St",
        bairro: String = "City",
        complemento: String = "Apt 1",
        estado: String = "State",
        localidade: String = "Country"
    ) = Contact(id, name, phone, cep, logradouro, bairro, complemento, estado, localidade)

    @Test
    fun `insertContact calls dao insert with converted entity`() = runTest {
        val contact = makeContact()
        repositoryImpl.insertContact(contact)
        coVerify { dao.insert(contact.toEntity()) }
    }

    @Test
    fun `updateContact calls dao update with converted entity`() = runTest {
        val contact  = makeContact()
        repositoryImpl.updateContact(contact)
        coVerify { dao.update(contact.toEntity()) }
    }

    @Test
    fun `deleteContact calls dao delete with converted entity`() = runTest {
        val contact = makeContact()
        repositoryImpl.deleteContact(contact)
        coVerify { dao.delete(contact.toEntity()) }
    }

    @Test
    fun `getContactById maps dao flow to domain contact`() = runTest {
        val contact = makeContact()
        coEvery { dao.getContactById(1) } returns flowOf(contact.toEntity())

        val resultFlow = repositoryImpl.getContactById(1)
        resultFlow.collect { result ->
            assertEquals("Junior", result?.name)
        }
    }

    @Test
    fun `getAllContacts maps dao flow to domain contact list`() = runTest {
        val contactEntities = listOf(
            makeContact().toEntity(),
            makeContact(id = 2, name = "Maria").toEntity()
        )
        coEvery { dao.getAllContacts() } returns flowOf(contactEntities)

        val resultFlow = repositoryImpl.getAllContacts()
        resultFlow.collect { resultList ->
            assertEquals(2, resultList.size)
            assertEquals("Junior", resultList[0].name)
            assertEquals("Maria", resultList[1].name)
        }
    }

    @Test
    fun `getContactByPhone returns a contact when found`() = runTest {
        val phone = "1234567890"
        val contactEntity = makeContact(phone = phone).toEntity()
        coEvery { dao.getContactByPhone(phone) } returns contactEntity
        val result = repositoryImpl.getContactByPhone(phone)
        assertEquals("Junior", result?.name)
    }

    @Test
    fun `getContactByPhone returns null when not found`() = runTest {
        val phone = "999999999"
        coEvery { dao.getContactByPhone(phone) } returns null
        val result = repositoryImpl.getContactByPhone(phone)
        assertEquals(null, result)
    }
}