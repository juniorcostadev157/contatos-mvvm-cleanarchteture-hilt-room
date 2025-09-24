package com.junior.contatosjetpackmvvmhilt.presentation.screens.details_contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.domain.usecase.DeleteContactUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.GetContactByIDUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class DetailsScreenViewModel@Inject constructor(
    private val getContactByIDUseCase: GetContactByIDUseCase,
    private val deleteContactUseCase: DeleteContactUseCase
): ViewModel() {


    private  val _uiState = MutableStateFlow(DetailsUIState())
    val uiState: StateFlow<DetailsUIState> = _uiState

    fun loadContact(id: Int){

       viewModelScope.launch {
          getContactByIDUseCase(id).onStart {
               _uiState.value = DetailsUIState(isLoading = true)
           }.catch {e->
               _uiState.value = DetailsUIState(
                   isLoading = false,
                   error = e.message ?: "Erro desconhecido"
               )

           }.collect {contact->
               _uiState.value = DetailsUIState(
                   isLoading = false,
                   contact = contact
               )

           }
       }
    }

    fun deleteContact(contact: Contact){

        viewModelScope.launch {
             deleteContactUseCase(contact)
        }

        }
    }
