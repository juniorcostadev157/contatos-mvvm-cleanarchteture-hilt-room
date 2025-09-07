package com.junior.contatosjetpackmvvmhilt.ui.viewmodel


import app.cash.turbine.test
import com.junior.contatosjetpackmvvmhilt.MainDispatcherRule
import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import com.junior.contatosjetpackmvvmhilt.data.usecase.DeleteContactUseCase
import com.junior.contatosjetpackmvvmhilt.data.usecase.GetContactByIDUseCase
import com.junior.contatosjetpackmvvmhilt.ui.screens.details_contact.DetailsScreenViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getContactByIDUseCase: GetContactByIDUseCase = mockk()
    private val deleteContactUseCase: DeleteContactUseCase = mockk(relaxed = true)

    private fun makeContact(
        id: Int = 1,
        name: String = "Junior",
        phone: String = "1234567890"
    ) = Contact(
        id = id,
        name = name,
        phone = phone,
        cep = "12345",
        logradouro = "Rua X",
        bairro = "Centro",
        complemento = "",
        estado = "SP",
        localidade = "Sao Paulo"
    )

    private fun buildViewModel() =
        DetailsScreenViewModel(getContactByIDUseCase, deleteContactUseCase)



    @Test
    fun `loadContact updates state with contact on success`() = runTest {
        val vm = buildViewModel()
        val contact = makeContact()

        coEvery { getContactByIDUseCase(1) } returns flowOf(contact)

        vm.uiState.test {
            vm.loadContact(1)

            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val success = awaitItem()
            assertEquals(contact, success.contact)
            assertEquals(false, success.isLoading)
            assertEquals(null, success.error)
        }
    }

    @Test
    fun `loadContact updates state with error on failure`() = runTest {
        val vm = buildViewModel()

        coEvery { getContactByIDUseCase(1) } returns flow {
            throw RuntimeException("Falha no banco")
        }

        vm.uiState.test {
            vm.loadContact(1)

            val loading = awaitItem()
            assertTrue(loading.isLoading)

            val errorState = awaitItem()
            assertEquals("Falha no banco", errorState.error)
            assertEquals(false, errorState.isLoading)
            assertEquals(null, errorState.contact)
        }
    }

    // -------- deleteContact --------

    @Test
    fun `deleteContact calls deleteContactUseCase`() = runTest {
        val vm = buildViewModel()
        val contact = makeContact()

        coEvery { deleteContactUseCase(contact) } returns Unit

        vm.deleteContact(contact)

        coVerify(exactly = 1) { deleteContactUseCase(contact) }
    }
}
