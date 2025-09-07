package com.junior.contatosjetpackmvvmhilt.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import com.junior.contatosjetpackmvvmhilt.data.usecase.GetAllContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel@Inject constructor(private val getAllContactsUseCase: GetAllContactsUseCase): ViewModel() {


   private val _uiState = MutableStateFlow(HomeUIState())
   val uiState: StateFlow<HomeUIState> = _uiState

   init {
       viewModelScope.launch {
          getAllContactsUseCase().collect { contacts->

             _uiState.value = _uiState.value.copy(
                isLoading = false,
                contacts = filterContacts(_uiState.value.query, contacts),
                allContacts = contacts
             )
          }
       }
   }

   fun onQueryChange(newQuery: String){
      _uiState.value = _uiState.value.copy(
         query = newQuery,
         contacts = filterContacts(newQuery, _uiState.value.allContacts)
      )
   }

   private fun filterContacts(query: String, contacts: List<Contact>): List<Contact>{
      if (query.isBlank()) return contacts
      return contacts.filter {
         it.name.contains(query, ignoreCase = true) || it.phone.contains(query)
      }

   }

   fun startSearch(){
      _uiState.value = _uiState.value.copy(
         isSearching = true
      )
   }

   fun closeSearch(){
      _uiState.value = _uiState.value.copy(
         isSearching = false,
         query = "",
         contacts = _uiState.value.allContacts
      )
   }
}