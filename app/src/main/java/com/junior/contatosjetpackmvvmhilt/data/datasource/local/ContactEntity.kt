package com.junior.contatosjetpackmvvmhilt.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name:String,
    val phone: String,
    val complemento: String,
    val cep: String,
    val localidade: String,
    val bairro: String,
    val estado: String,
    val logradouro: String
)
