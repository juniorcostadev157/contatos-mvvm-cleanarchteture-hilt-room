package com.junior.contatosjetpackmvvmhilt.data.datasource.remote

import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CepApiService {
    @GET("{cep}/json")
    suspend fun  getCepInfo(@Path("cep")cep : String): CepResponse
}