package com.junior.contatosjetpackmvvmhilt.data.model

data class Contact(
    val id: Int = 0,
    val name: String,
    val phone: String,
    val complemento: String,
    val cep: String,
    val localidade: String,
    val estado: String,
    val bairro: String,
    val logradouro: String

)
