package com.junior.contatosjetpackmvvmhilt.domain.usecase

import com.junior.contatosjetpackmvvmhilt.domain.model.Contact
import com.junior.contatosjetpackmvvmhilt.domain.repository.local.ContactRepository
import javax.inject.Inject

sealed class ValidationResult {
    object Success: ValidationResult()
    object EmptyField: ValidationResult()
    object AlreadyExists: ValidationResult()
    }


class ValidateContactUseCase @Inject constructor(private val repository: ContactRepository){

    suspend operator fun invoke(contact: Contact, isUpdate: Boolean = false): ValidationResult{
        //campos obrigatorios
        if (contact.name.isBlank() || contact.phone.isBlank() || contact.complemento.isBlank() || contact.cep.isBlank() ){
            return ValidationResult.EmptyField
        }

        val  existing  = repository.getContactByPhone(contact.phone)
        if (existing != null){
            if (!isUpdate || existing.id != contact.id){
                return ValidationResult.AlreadyExists

            }
        }

        return ValidationResult.Success
    }
}