package com.junior.contatosjetpackmvvmhilt.presentation.screens.update_contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.junior.contatosjetpackmvvmhilt.core.contants.Constants
import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.domain.usecase.GetCepInfoUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.UpdateContactUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.ValidateContactUseCase
import com.junior.contatosjetpackmvvmhilt.domain.usecase.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateContactViewModel @Inject constructor(
    private val updateContactUseCase: UpdateContactUseCase,
    private val getCepInfoUseCase: GetCepInfoUseCase,
    private val validateContactUseCase: ValidateContactUseCase): ViewModel() {

    private val _nome = MutableStateFlow("")
    var nome: StateFlow<String> = _nome

    private val _phone = MutableStateFlow("")
    var phone: StateFlow<String> = _phone

    private val _complemento = MutableStateFlow("")
    var complemento: StateFlow<String> = _complemento

    private val _cep = MutableStateFlow("")
    var cep: StateFlow<String> = _cep

    private val _bairro = MutableStateFlow("")
    var bairro: StateFlow<String> = _bairro

    private val _logradouro = MutableStateFlow("")
    var logradouro: StateFlow<String> = _logradouro

    private val _localidade = MutableStateFlow("")
    var localidade: StateFlow<String> = _localidade

    private val _estado = MutableStateFlow("")
    var estado: StateFlow<String> = _estado


    private val _eventFlow = MutableSharedFlow<String>()
    var eventFlow = _eventFlow.asSharedFlow()

    fun setNome(value: String){_nome.value = value}
    fun setPhone(value: String){_phone.value = value}
    fun setComplemento(value: String){_complemento.value = value}

    fun setCep(value: String){
        _cep.value = value
        if (value.length == 8){
            fetchCepInfo(value)
        }
    }
    fun setBairro(value: String){_bairro.value = value}
    fun setLogradouro(value: String){_logradouro.value = value}
    fun setLocalidade(value: String){_localidade.value = value}
    fun setEstado(value: String){_estado.value = value}

    fun fetchCepInfo(cep: String){
        viewModelScope.launch {
            try {
                val response = getCepInfoUseCase(cep)
                _logradouro.value = response.logradouro ?: ""
                _bairro.value = response.bairro ?: ""
                _localidade.value = response.localidade ?: ""
                _estado.value = response.estado ?: ""
            }catch (e: Exception){
                _eventFlow.emit(e.message ?: Constants.Message.MESSAGE_ERROR_SEARCH_CEP)
            }
        }
    }

    fun updateContact(contact: Contact){
        viewModelScope.launch {
            when(validateContactUseCase(contact, isUpdate = true)){
                ValidationResult.EmptyField -> {
                    _eventFlow.emit(Constants.Message.MESSAGE_EMPTY_FIELDS)
                }
                ValidationResult.AlreadyExists -> {
                    _eventFlow.emit(Constants.Message.MESSAGE_EXISTS_CONTACT)
                }

                ValidationResult.Success -> {
                    _eventFlow.emit(Constants.Message.MESSAGE_SUCCESS_UPDATE)
                    updateContactUseCase(contact)
                }
            }
        }
    }



}