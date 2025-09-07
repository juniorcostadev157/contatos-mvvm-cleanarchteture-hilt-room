package com.junior.contatosjetpackmvvmhilt.ui.viewmodel

import app.cash.turbine.test
import com.junior.contatosjetpackmvvmhilt.MainDispatcherRule
import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import com.junior.contatosjetpackmvvmhilt.data.usecase.GetAllContactsUseCase
import com.junior.contatosjetpackmvvmhilt.ui.screens.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getAllContactsUseCase: GetAllContactsUseCase = mockk()

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

    private fun buildViewModel() = HomeViewModel(getAllContactsUseCase)

    // -------- init / getAllContactsUseCase --------
    @Test
    fun `init loads all contacts and updates uiState`() = runTest {
        val contacts = listOf(makeContact(), makeContact(id = 2, name = "Maria"))
        coEvery { getAllContactsUseCase() } returns flow { emit(contacts) }

        val vm = buildViewModel()

        vm.uiState.test {
            val state = awaitItem()
            assertEquals(false, state.isLoading)
            assertEquals(contacts, state.contacts)
            assertEquals(contacts, state.allContacts)
        }
    }

    // -------- onQueryChange --------
    @Test
    fun `onQueryChange filters contacts by name`() = runTest {
        val contacts = listOf(makeContact(), makeContact(id = 2, name = "Maria"))
        coEvery { getAllContactsUseCase() } returns flow { emit(contacts) }

        val vm = buildViewModel()

        vm.onQueryChange("Maria")
        val filtered = vm.uiState.value.contacts
        assertEquals(1, filtered.size)
        assertEquals("Maria", filtered[0].name)
    }

    @Test
    fun `onQueryChange filters contacts by phone`() = runTest {
        val contacts = listOf(makeContact(), makeContact(id = 2, phone = "987654321"))
        coEvery { getAllContactsUseCase() } returns flow { emit(contacts) }

        val vm = buildViewModel()

        vm.onQueryChange("987654321")
        val filtered = vm.uiState.value.contacts
        assertEquals(1, filtered.size)
        assertEquals("987654321", filtered[0].phone)
    }

    @Test
    fun `onQueryChange with blank query returns all contacts`() = runTest {
        val contacts = listOf(makeContact(), makeContact(id = 2, name = "Maria"))
        coEvery { getAllContactsUseCase() } returns flow { emit(contacts) }

        val vm = buildViewModel()

        vm.onQueryChange("")
        val filtered = vm.uiState.value.contacts
        assertEquals(contacts, filtered)
    }

    // -------- startSearch / closeSearch --------
    @Test
    fun `startSearch sets isSearching to true`() = runTest {
        val contacts = listOf(makeContact())
        coEvery { getAllContactsUseCase() } returns flow { emit(contacts) }

        val vm = buildViewModel()
        vm.startSearch()
        val state = vm.uiState.value
        assertTrue(state.isSearching)
    }

    @Test
    fun `closeSearch resets isSearching and query`() = runTest {
        val contacts = listOf(makeContact())
        coEvery { getAllContactsUseCase() } returns flow { emit(contacts) }

        val vm = buildViewModel()
        vm.startSearch()
        vm.onQueryChange("junior")
        vm.closeSearch()

        val state = vm.uiState.value
        assertEquals(false, state.isSearching)
        assertEquals("", state.query)
        assertEquals(contacts, state.contacts)
    }
}
