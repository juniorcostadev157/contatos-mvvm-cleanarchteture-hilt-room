package com.junior.contatosjetpackmvvmhilt.domain.repository.remote

import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse

interface  CepRepository {

    suspend fun findCep(cep: String): CepResponse
}