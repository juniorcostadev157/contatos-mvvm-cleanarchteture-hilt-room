package com.junior.contatosjetpackmvvmhilt.data.repository.remote

import com.junior.contatosjetpackmvvmhilt.data.datasource.remote.CepApiService
import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse
import javax.inject.Inject

interface  CepRepository {

    suspend fun findCep(cep: String): CepResponse
}