package com.junior.contatosjetpackmvvmhilt.domain.usecase

import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse
import com.junior.contatosjetpackmvvmhilt.domain.repository.remote.CepRepository
import com.junior.contatosjetpackmvvmhilt.domain.usecase.GetCepInfoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCepInfoUseCaseTest {

    private lateinit var cepRepository: CepRepository
    private lateinit var getCepInfoUseCase: GetCepInfoUseCase

    @Before
    fun setup(){

        cepRepository = mockk()
        getCepInfoUseCase = GetCepInfoUseCase(cepRepository)

    }

    private fun makeCepResponse(
        cep: String = "12345-678",
        logradouro: String = "Rua Exemplo",
        bairro: String = "Bairro Exemplo",
        localidade: String = "Cidade Exemplo",
        estado: String = "Estado Exemplo"
    ) = CepResponse(cep, logradouro, bairro, localidade, estado)

    @Test
    fun `invoke should return CepResponse from repository`() = runTest {
        val cepResponse = makeCepResponse()
        coEvery { cepRepository.findCep("12345-678") } returns cepResponse
        val result  = getCepInfoUseCase("12345-678")
        assertEquals(cepResponse, result)
        coVerify (exactly = 1){ cepRepository.findCep("12345-678") }

    }

    @Test
    fun `invoke should call repository with correct cep`() = runTest {
        val cep = "12345"
        val cepResponse = makeCepResponse(cep = cep)
        coEvery { cepRepository.findCep(cep) } returns cepResponse
        getCepInfoUseCase(cep)
        coVerify (exactly = 1){ cepRepository.findCep(cep) }


    }
}