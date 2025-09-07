package com.junior.contatosjetpackmvvmhilt.ui.screens.add_contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.contatosjetpackmvvmhilt.data.model.Contact
import com.junior.contatosjetpackmvvmhilt.core.contants.Constants
import com.junior.contatosjetpackmvvmhilt.data.usecase.GetCepInfoUseCase
import com.junior.contatosjetpackmvvmhilt.data.usecase.InsertContactsUseCase
import com.junior.contatosjetpackmvvmhilt.data.usecase.ValidateContactUseCase
import com.junior.contatosjetpackmvvmhilt.data.usecase.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val insertContactsUseCase: InsertContactsUseCase,
    private val validateContactUseCase: ValidateContactUseCase,
    private val getCepInfoUseCase: GetCepInfoUseCase): ViewModel() {

    private val _name = MutableStateFlow("")
    var name: StateFlow<String> = _name

    private val _phone = MutableStateFlow("")
    var phone: StateFlow<String> = _phone

    private val _complemento = MutableStateFlow("")
    var complemento: StateFlow<String> = _complemento

    private val _cep = MutableStateFlow("")
    var cep: StateFlow<String> = _cep

    private val _logradouro = MutableStateFlow("")
    var logradouro: StateFlow<String> = _logradouro

    private val _bairro = MutableStateFlow("")
    var bairro: StateFlow<String> = _bairro

    private val _localidade  = MutableStateFlow("")
    var localidade: StateFlow<String> = _localidade

    private val _estado = MutableStateFlow("")
    var estado: StateFlow<String> = _estado

    private val _eventFlow = MutableSharedFlow<String>()
    val eventFlow = _eventFlow.asSharedFlow()



        fun setName(value: String){_name.value=value}
        fun setPhone(value: String){_phone.value=value}
        fun setComplemento(value: String){_complemento.value=value}
        fun setCep(value: String){
            _cep.value=value
            if (value.length == 8){
                    fetchCepInfo(value)
            }
        }

    fun insertContact(contact: Contact){
        viewModelScope.launch {
        when(validateContactUseCase(contact)){
            ValidationResult.EmptyField->{
                _eventFlow.emit(Constants.MESSAGE.MESSAGE_EMPTY_FIELDS)
            }

            ValidationResult.AlreadyExists->{
                _eventFlow.emit(Constants.MESSAGE.MESSAGE_EXISTS_CONTACT)
            }

            ValidationResult.Success->{
                insertContactsUseCase(contact)
                _eventFlow.emit(Constants.MESSAGE.MESSAGE_SUCCESS_INSERT)
            }
        }
        }

    }

    fun fetchCepInfo(cep: String){
        viewModelScope.launch {
            try {
                val response = getCepInfoUseCase(cep)
                _logradouro.value = response.logradouro ?: ""
                _bairro.value = response.bairro ?: ""
                _localidade.value = response.localidade ?: ""
                _estado.value = response.estado ?: ""
            }catch (e: Exception){
                _eventFlow.emit(e.message ?: Constants.MESSAGE.MESSAGE_ERROR_SEARCH_CEP)
            }
        }
    }
    }
