package com.junior.contatosjetpackmvvmhilt.data.repository.remote

import com.junior.contatosjetpackmvvmhilt.data.datasource.remote.CepApiService
import com.junior.contatosjetpackmvvmhilt.data.model.CepResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import kotlin.test.assertEquals

class CepRepositoryTest {
    /*
    deve chamar apiService.getCepInfo(cep) com o CEP correto.
    Deve retornar o resultado que o service devolve (sem alterar nada).

     */

    private val apiService = mockk<CepApiService>()
    private lateinit var repository: CepRepositoryImpl

    @Before
    fun setup(){
        repository = CepRepositoryImpl(apiService)
    }

    @Test
    fun `findCep calls apiService and returns reponse`() = runTest {
        val cep = "12345-678"
        val response = CepResponse(
            cep = cep,
            logradouro = "Rua Exemplo",
             bairro = "Bairro Exemplo",
            localidade = "Cidade Exemplo",
            estado = "Estado Exemplo"
        )

        coEvery { apiService.getCepInfo(cep) } returns response
        val result = repository.findCep(cep)
        assertEquals(response,result)
        coEvery { apiService.getCepInfo(cep) }
    }

}