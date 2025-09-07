📱 Contatos App (MVVM + Clean Architecture)

Aplicativo de gerenciamento de contatos desenvolvido em Kotlin com arquitetura MVVM + Clean Architecture, utilizando Room, Retrofit e Hilt. Testes unitários e de integração implementados em todas as camadas.

🏗 Arquitetura e Tecnologias

Arquitetura: MVVM + Clean Architecture

Injeção de Dependência: Hilt

Persistência Local: Room

Requisições HTTP: Retrofit

Mapeamento de dados: Mapper functions (toEntity)

Gerenciamento de Estado: StateFlow e SharedFlow

Validações e regras de negócio: UseCases (Insert, Update, Delete, Validate, GetByID, etc.)

🔹 Funcionalidades

CRUD completo de contatos

Busca por nome e telefone

Validação de campos e duplicidade

Consulta de endereço via CEP (integração com API externa)

Atualização de contatos com feedback em tempo real

🧪 Testes

Testes implementados em todas as camadas do app, garantindo alta confiabilidade:

1️⃣ Repository

Local (ContactRepositoryImpl) e Remote (CepRepositoryImpl)

Testa insert, update, delete, getById, getAll, getByPhone

MockK para mockar Dao e API Service

2️⃣ UseCases

Testa todos os casos de uso:

InsertContactsUseCase, DeleteContactUseCase, UpdateContactUseCase

GetAllContactsUseCase, GetContactByIDUseCase, GetContactByPhoneUseCase

ValidateContactUseCase (campos vazios, duplicados, sucesso)

Flow validado com .toList()

3️⃣ ViewModel

Testa estados iniciais e mudanças de estado (StateFlow)

Emissão de eventos (SharedFlow)

Integração com UseCases mockados

Libraries usadas: MockK, Turbine, kotlinx.coroutines.test

4️⃣ Room / Dao

Testes com banco de dados in-memory

Funcionalidades: insert, update, delete, getAllContacts, getContactByPhone

Validação de constraints (duplicidade e integridade)

⚡ Bibliotecas de Teste

MockK → Mock de classes e funções suspensas

kotlinx.coroutines.test → Testes de coroutines (runTest)

JUnit4 / Kotlin Test → Estrutura de testes unitários

Turbine → Teste de Flows (StateFlow e SharedFlow)

AndroidX Test → Testes de Room em memória
