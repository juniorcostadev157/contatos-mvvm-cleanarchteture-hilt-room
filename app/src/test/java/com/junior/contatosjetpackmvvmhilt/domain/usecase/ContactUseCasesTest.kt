package com.junior.contatosjetpackmvvmhilt.domain.usecase

    import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
    import com.junior.contatosjetpackmvvmhilt.domain.repository.local.ContactRepository
    import com.junior.contatosjetpackmvvmhilt.domain.usecase.DeleteContactUseCase
    import com.junior.contatosjetpackmvvmhilt.domain.usecase.GetAllContactsUseCase
    import com.junior.contatosjetpackmvvmhilt.domain.usecase.GetContactByIDUseCase
    import com.junior.contatosjetpackmvvmhilt.domain.usecase.GetContactByPhoneUseCase
    import com.junior.contatosjetpackmvvmhilt.domain.usecase.InsertContactsUseCase
    import com.junior.contatosjetpackmvvmhilt.domain.usecase.UpdateContactUseCase
    import com.junior.contatosjetpackmvvmhilt.domain.usecase.ValidateContactUseCase
    import com.junior.contatosjetpackmvvmhilt.domain.usecase.ValidationResult
    import io.mockk.coEvery
    import io.mockk.coVerify
    import io.mockk.mockk
    import kotlinx.coroutines.flow.flowOf
    import kotlinx.coroutines.flow.toList
    import kotlinx.coroutines.test.runTest
    import org.junit.Before
    import kotlin.test.Test
    import kotlin.test.assertTrue


    class ContactUseCasesTest {

        private lateinit var  contactRepository: ContactRepository

        //UseCases
        private lateinit var  insertContactsUseCase: InsertContactsUseCase
        private lateinit var deleteContactUseCase: DeleteContactUseCase
        private lateinit var validateContactUseCase: ValidateContactUseCase
        private lateinit var getAllContactsUseCase: GetAllContactsUseCase
        private lateinit var getContactByPhoneUseCase: GetContactByPhoneUseCase
        private lateinit var getContactByIDUseCase: GetContactByIDUseCase

        @Before
        fun setup(){
            contactRepository = mockk()
            insertContactsUseCase = InsertContactsUseCase(contactRepository)
            deleteContactUseCase = DeleteContactUseCase(contactRepository)
            validateContactUseCase = ValidateContactUseCase(contactRepository)
            getAllContactsUseCase = GetAllContactsUseCase(contactRepository)
            getContactByPhoneUseCase = GetContactByPhoneUseCase(contactRepository)
            getContactByIDUseCase = GetContactByIDUseCase(contactRepository)

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


        )= Contact(id, name, phone, cep, logradouro, bairro, complemento, estado, localidade)
        @Test
        fun `insertContactUseCase should call repository `() = runTest {
            val contact = makeContact()
            coEvery { contactRepository.insertContact(contact) } returns Unit
            insertContactsUseCase(contact)
            coVerify (exactly = 1){contactRepository.insertContact(contact)}

        }
        @Test
        fun `delete contact should call repository`() = runTest {
            val contact  = makeContact()
            coEvery { contactRepository.deleteContact(contact) } returns Unit
            deleteContactUseCase(contact)
            coVerify(exactly = 1) {contactRepository.deleteContact(contact)}
        }
        @Test
        @Suppress("kotlinx.coroutines.FlowConstructedButNotUsed")
        fun `getAllContactUseCase should return list from repository`() = runTest {
            val contactList = listOf(makeContact(), makeContact(id = 2))
            coEvery { contactRepository.getAllContacts() } returns flowOf(contactList)

            val result = getAllContactsUseCase().toList()
            assertTrue( result[0] == contactList)
            coVerify(exactly = 1){contactRepository.getAllContacts()}
        }

        @Test
        fun `getContactByIdUseCase  should return contact by id`() = runTest {
            val contact  = makeContact()
            coEvery { contactRepository.getContactById(1) } returns flowOf(contact)
            val useCase = getContactByIDUseCase
            val result  = useCase(1).toList()
            assertTrue (result[0] == contact)
            coVerify (exactly = 1){contactRepository.getContactById(1)}
        }

        @Test
        fun `getContactByPhoneUseCase should return contact by phone`() = runTest {
            val contact = makeContact()
            coEvery { contactRepository.getContactByPhone("1234567890") } returns contact

            val useCase = GetContactByPhoneUseCase(contactRepository)
            val result = useCase("1234567890")

            assertTrue(result == contact)
            coVerify(exactly = 1) { contactRepository.getContactByPhone("1234567890") }
        }

        @Test
        fun `updateContactUseCase should call repository`() = runTest {
            val contact = makeContact()
            coEvery { contactRepository.updateContact(contact) } returns Unit

            val useCase = UpdateContactUseCase(contactRepository)
            useCase(contact)

            coVerify(exactly = 1) { contactRepository.updateContact(contact) }
        }



        @Test
        fun `validate contact should return error when name is blank`() = runTest{
            val contact = makeContact(name = "")
            coEvery { contactRepository.getContactByPhone(any()) } returns null
            val result  = validateContactUseCase(contact)
            assertTrue (result is ValidationResult.EmptyField)
        }

        @Test
        fun `validate contact should return AlreadyExists when contact already exists`() = runTest {
            val existingContact  =  makeContact(1, phone = "123456")
            val newContact = makeContact(2, phone = "123456")
            coEvery { contactRepository.getContactByPhone("123456") } returns existingContact

            val result  = validateContactUseCase(newContact)
            assertTrue(result is ValidationResult.AlreadyExists)
        }

        @Test
        fun `validate contact should return Success when contact is valid and phone no exists`() = runTest {

            val contact = makeContact()
            coEvery { contactRepository.getContactByPhone(contact.phone) } returns null

            val result  = validateContactUseCase(contact)

            assertTrue(result is ValidationResult.Success)

        }








    }