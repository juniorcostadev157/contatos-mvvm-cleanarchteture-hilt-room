package com.junior.contatosjetpackmvvmhilt.domain.model.mapper

import com.junior.contatosjetpackmvvmhilt.data.datasource.local.ContactEntity
import com.junior.contatosjetpackmvvmhilt.domain.model.Contact

fun Contact.toEntity(): ContactEntity{
    return ContactEntity(
        id = id,
        name = name,
        phone = phone,
        complemento = complemento,
        cep = cep,
        localidade = localidade,
        estado = estado,
        bairro = bairro,
        logradouro = logradouro


    )
}

fun ContactEntity.toDomain():Contact{
    return Contact(
        id = id,
        name = name,
        phone = phone,
        complemento = complemento,
        cep = cep,
        localidade = localidade,
        estado = estado,
        bairro = bairro,
        logradouro = logradouro
    )
}