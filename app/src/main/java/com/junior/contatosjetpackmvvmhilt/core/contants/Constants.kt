package com.junior.contatosjetpackmvvmhilt.core.contants

class Constants {

    object MESSAGE {
        const val MESSAGE_SUCCESS_INSERT = "Contato inserido com sucesso"
        const val MESSAGE_SUCCESS_UPDATE = "Contato atualizado com sucesso"
        const val MESSAGE_SUCCESS_DELETE = "Contato deletado com sucesso"
        const val MESSAGE_EMPTY_FIELDS = "Preencha todos os campos"
        const val MESSAGE_EXISTS_CONTACT = "Contato já existe"
        const val MESSAGE_ERROR_SEARCH_CEP = "Erro ao buscar CEP"
    }

    object TEXT_FIELD {
        const val NAME = "Nome"
        const val PHONE = "Telefone"
        const val COMPLEMENTO = "Complemento"
        const val CEP = "CEP"
        const val BAIRRO = "Bairro"
        const val LOGRADOURO = "Logradouro"
        const val LOCALIDADE = "Localidade"
        const val ESTADO = "Estado"
    }

    object NavRoutes {
        const val HOME = "home"
        const val ADD_CONTACT = "add_contact"
        const val DETAILS_CONTACT = "details_contact"
        const val EDIT_CONTACT = "edit_contact"
    }
    object ApiUrl{
      const val  BASE_URL_VIA_CEP = "https://viacep.com.br/ws/"
    }

    object Text{
        const val PHONE = "Telefone"
        const val COMPLEMENTO = "Complemento"
        const val CEP = "Cep"
        const val LOGRADOURO = "Logradouro"
        const val BAIRRO = "Bairro"
        const val LOCALIDADE = "Localidade"
        const val ESTADO = "Estado"
        const val NAME = "Nome"



    }
}