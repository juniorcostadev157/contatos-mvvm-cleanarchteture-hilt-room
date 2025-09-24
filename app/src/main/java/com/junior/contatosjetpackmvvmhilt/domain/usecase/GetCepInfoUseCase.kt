package com.junior.contatosjetpackmvvmhilt.domain.usecase

import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse
import com.junior.contatosjetpackmvvmhilt.domain.repository.remote.CepRepository
import javax.inject.Inject

class GetCepInfoUseCase @Inject constructor(private val repository: CepRepository) {

    suspend operator fun invoke(cep: String): CepResponse{
        return repository.findCep(cep)
    }
}