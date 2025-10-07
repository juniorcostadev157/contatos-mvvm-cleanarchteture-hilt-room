package com.junior.contatosjetpackmvvmhilt.presentation.viewmodel

import app.cash.turbine.test
import com.junior.contatosjetpackmvvmhilt.MainDispatcherRule
import com.junior.contatosjetpackmvvmhilt.core.contants.Constants
import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse
import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.domain.usecase.GetCepInfoUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.InsertContactsUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.ValidateContactUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.ValidationResult
import com.junior.contatosjetpackmvvmhilt.presentation.screens.add_contact.AddContactViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AddContactViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule() // usa UnconfinedTestDispatcher

    // mocks dos use cases
    private val insertContactsUseCase: InsertContactsUseCase = mockk(relaxed = true)
    private val validateContactUseCase: ValidateContactUseCase = mockk()
    private val getCepInfoUseCase: GetCepInfoUseCase = mockk()

    private fun makeContact(
        id: Int = 1,
        name: String = "Junior",
        phone: String = "1234567890",
        cep: String = "",
        logradouro: String = "",
        bairro: String = "",
        complemento: String = "",
        estado: String = "",
        localidade: String = ""
    ) = Contact(id, name, phone, cep, logradouro, bairro, complemento, estado, localidade)

    private fun buildViewModel() =
        AddContactViewModel(insertContactsUseCase, validateContactUseCase, getCepInfoUseCase)

    // --------- insertContact ---------

    @Test
    fun `insertContact emits success message when contact is valid`() = runTest {
        val vm = buildViewModel()
        val contact = makeContact()

        coEvery { validateContactUseCase(contact) } returns ValidationResult.Success
        coEvery { insertContactsUseCase(contact) } returns Unit

        vm.eventFlow.test {
            vm.insertContact(contact)

            assertEquals(Constants.Message.MESSAGE_SUCCESS_INSERT, awaitItem())
            coVerify(exactly = 1) { insertContactsUseCase(contact) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insertContact emits empty fields message when validation fails`() = runTest {
        val vm = buildViewModel()
        val contact = makeContact(name = "")

        coEvery { validateContactUseCase(contact) } returns ValidationResult.EmptyField

        vm.eventFlow.test {
            vm.insertContact(contact)

            assertEquals(Constants.Message.MESSAGE_EMPTY_FIELDS, awaitItem())
            coVerify(exactly = 0) { insertContactsUseCase(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insertContact emits already exists message when phone already used`() = runTest {
        val vm = buildViewModel()
        val contact = makeContact()

        coEvery { validateContactUseCase(contact) } returns ValidationResult.AlreadyExists

        vm.eventFlow.test {
            vm.insertContact(contact)

            assertEquals(Constants.Message.MESSAGE_EXISTS_CONTACT, awaitItem())
            coVerify(exactly = 0) { insertContactsUseCase(any()) }
            cancelAndIgnoreRemainingEvents()
        }
    }



    @Test
    fun `setCep with 8 chars triggers fetch and updates address fields`() = runTest {
        val vm = buildViewModel()
        val cep = "12345678"

        // mocka CepResponse só com os getters usados
        val resp = mockk<CepResponse> {
            io.mockk.every { logradouro } returns "Rua A"
            io.mockk.every { bairro } returns "Centro"
            io.mockk.every { localidade } returns "Sao Paulo"
            io.mockk.every { estado } returns "SP"
        }
        coEvery { getCepInfoUseCase(cep) } returns resp

        vm.setCep(cep)

        assertEquals("Rua A", vm.logradouro.value)
        assertEquals("Centro", vm.bairro.value)
        assertEquals("Sao Paulo", vm.localidade.value)
        assertEquals("SP", vm.estado.value)
        coVerify(exactly = 1) { getCepInfoUseCase(cep) }
    }

    @Test
    fun `setCep with less than 8 chars does not call fetch`() = runTest {
        val vm = buildViewModel()

        vm.setCep("1234567") // 7 chars

        coVerify(exactly = 0) { getCepInfoUseCase(any()) }
    }

    @Test
    fun `fetchCepInfo emits error message when repository throws`() = runTest {
        val vm = buildViewModel()
        val cep = "87654321"

        coEvery { getCepInfoUseCase(cep) } throws RuntimeException("Falha ao buscar CEP")

        vm.eventFlow.test {
            vm.fetchCepInfo(cep)

            assertEquals("Falha ao buscar CEP", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `setName, setPhone and setComplemento update state flows`() {
        val vm = buildViewModel()

        vm.setName("Junior")
        vm.setPhone("99999999")
        vm.setComplemento("Apto 101")

        assertEquals("Junior", vm.name.value)
        assertEquals("99999999", vm.phone.value)
        assertEquals("Apto 101", vm.complemento.value)
    }

    @Test
    fun `fetchCepInfo updates state when repository returns data`() = runTest {
        val vm = buildViewModel()
        val cep = "11222333"

        val resp = mockk<CepResponse> {
            io.mockk.every { logradouro } returns "Rua X"
            io.mockk.every { bairro } returns "Bairro Y"
            io.mockk.every { localidade } returns "Cidade Z"
            io.mockk.every { estado } returns "ZZ"
        }
        coEvery { getCepInfoUseCase(cep) } returns resp

        vm.fetchCepInfo(cep)

        assertEquals("Rua X", vm.logradouro.value)
        assertEquals("Bairro Y", vm.bairro.value)
        assertEquals("Cidade Z", vm.localidade.value)
        assertEquals("ZZ", vm.estado.value)
    }
}
