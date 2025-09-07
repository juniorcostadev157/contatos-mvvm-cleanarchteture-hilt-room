package com.junior.contatosjetpackmvvmhilt.data.repository.remote

import com.junior.contatosjetpackmvvmhilt.data.datasource.remote.CepApiService
import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse
import javax.inject.Inject

class CepRepositoryImpl @Inject constructor(private val apiService: CepApiService) : CepRepository  {

    override suspend fun findCep(cep: String): CepResponse {
        return apiService.getCepInfo(cep)
    }
}