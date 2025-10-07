package com.junior.contatosjetpackmvvmhilt.domain.model

import com.junior.contatosjetpackmvvmhilt.data.datasource.local.ContactEntity

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