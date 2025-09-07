package com.junior.contatosjetpackmvvmhilt.data.usecase

import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse
import com.junior.contatosjetpackmvvmhilt.data.repository.remote.CepRepository
import javax.inject.Inject

class GetCepInfoUseCase @Inject constructor(private val repository: CepRepository) {

    suspend operator fun invoke(cep: String): CepResponse{
        return repository.findCep(cep)
    }
}