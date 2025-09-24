package com.junior.contatosjetpackmvvmhilt.presentation.viewmodel

import app.cash.turbine.test
import com.junior.contatosjetpackmvvmhilt.MainDispatcherRule
import com.junior.contatosjetpackmvvmhilt.core.contants.Constants
import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse
import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.domain.usecase.GetCepInfoUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.UpdateContactUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.ValidateContactUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.ValidationResult
import com.junior.contatosjetpackmvvmhilt.presentation.screens.update_contact.UpdateContactViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateContactViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val updateContactUseCase: UpdateContactUseCase = mockk(relaxed = true)
    private val getCepInfoUseCase: GetCepInfoUseCase = mockk()
    private val validateContactUseCase: ValidateContactUseCase = mockk()

    private fun makeContact(
        id: Int = 1,
        name: String = "Junior",
        phone: String = "1234567890"
    ) = Contact(
        id = id,
        name = name,
        phone = phone,
        cep = "",
        logradouro = "",
        bairro = "",
        complemento = "",
        estado = "",
        localidade = ""
    )

    private fun buildViewModel() =
        UpdateContactViewModel(updateContactUseCase, getCepInfoUseCase, validateContactUseCase)

    // --------- setters + fetchCepInfo ---------
    @Test
    fun `setCep with 8 chars triggers fetch and updates address fields`() = runTest {
        val vm = buildViewModel()
        val cep = "12345678"
        val resp = mockk<CepResponse> {
            io.mockk.every { logradouro } returns "Rua Teste"
            io.mockk.every { bairro } returns "Centro"
            io.mockk.every { localidade } returns "Cidade"
            io.mockk.every { estado } returns "SP"
        }
        coEvery { getCepInfoUseCase(cep) } returns resp

        vm.setCep(cep)

        assertEquals("Rua Teste", vm.logradouro.value)
        assertEquals("Centro", vm.bairro.value)
        assertEquals("Cidade", vm.localidade.value)
        assertEquals("SP", vm.estado.value)
        coVerify(exactly = 1) { getCepInfoUseCase(cep) }
    }

    @Test
    fun `fetchCepInfo emits error message when exception occurs`() = runTest {
        val vm = buildViewModel()
        val cep = "87654321"
        coEvery { getCepInfoUseCase(cep) } throws RuntimeException("Erro CEP")

        vm.eventFlow.test {
            vm.fetchCepInfo(cep)
            assertEquals("Erro CEP", awaitItem())
        }
    }

    // --------- updateContact ---------
    @Test
    fun `updateContact emits empty fields message`() = runTest {
        val vm = buildViewModel()
        val contact = makeContact()
        coEvery { validateContactUseCase(contact, isUpdate = true) } returns ValidationResult.EmptyField

        vm.eventFlow.test {
            vm.updateContact(contact)
            assertEquals(Constants.MESSAGE.MESSAGE_EMPTY_FIELDS, awaitItem())
            coVerify(exactly = 0) { updateContactUseCase(contact) }
        }
    }

    @Test
    fun `updateContact emits already exists message`() = runTest {
        val vm = buildViewModel()
        val contact = makeContact()
        coEvery { validateContactUseCase(contact, isUpdate = true) } returns ValidationResult.AlreadyExists

        vm.eventFlow.test {
            vm.updateContact(contact)
            assertEquals(Constants.MESSAGE.MESSAGE_EXISTS_CONTACT, awaitItem())
            coVerify(exactly = 0) { updateContactUseCase(contact) }
        }
    }

    @Test
    fun `updateContact emits success message and calls updateUseCase`() = runTest {
        val vm = buildViewModel()
        val contact = makeContact()
        coEvery { validateContactUseCase(contact, isUpdate = true) } returns ValidationResult.Success
        coEvery { updateContactUseCase(contact) } returns Unit

        vm.eventFlow.test {
            vm.updateContact(contact)
            assertEquals(Constants.MESSAGE.MESSAGE_SUCCESS_UPDATE, awaitItem())
            coVerify(exactly = 1) { updateContactUseCase(contact) }
        }
    }

    // --------- setters simples ---------
    @Test
    fun `setters update state flows`() = runTest {
        val vm = buildViewModel()
        vm.setNome("Teste")
        vm.setPhone("999")
        vm.setComplemento("Comp")
        vm.setBairro("Bairro")
        vm.setLogradouro("Log")
        vm.setLocalidade("Local")
        vm.setEstado("ES")

        assertEquals("Teste", vm.nome.value)
        assertEquals("999", vm.phone.value)
        assertEquals("Comp", vm.complemento.value)
        assertEquals("Bairro", vm.bairro.value)
        assertEquals("Log", vm.logradouro.value)
        assertEquals("Local", vm.localidade.value)
        assertEquals("ES", vm.estado.value)
    }
}
