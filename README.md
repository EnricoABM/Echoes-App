# Introdução
Aplicação mobile desenvolvida para interação com o sistema Echoes, responsável pelo monitoramento e gestão de dados provenientes de dispositivos IoT.
O aplicativo permite que usuários autenticados acessem informações do sistema, realizem consultas e interajam com funcionalidades disponibilizadas pela plataforma.
A comunicação com o backend ocorre por meio de uma API REST, responsável pela autenticação de usuários e pela gestão de dados do sistema.

## Tecnologias Utilizadas
O projeto foi desenvolvido utilizando as seguintes tecnologias:
* Kotlin
* Android Studio
* Jetpack Compose
* Retrofit
* OkHttp
* Android Jetpack ViewModel
* DataStore

## Comunicação com a API

O aplicativo se comunica com o servidor por meio de requisições HTTP utilizando a biblioteca Retrofit.
Após o processo de autenticação, o servidor retorna um token JWT que deve ser incluído no cabeçalho das requisições subsequentes:

```
Authorization: Bearer $token
```

Esse token é armazenado localmente no dispositivo e utilizado para autenticar futuras requisições

## Como Executar o Projeto
### 1. Pré-requisitos

Para executar o projeto é necessário possuir instalado:
* Android Studio
* Android SDK
* Emulador Android ou dispositivo físico

### 2. Clonar o repositório
```bash
git clone https://github.com/EnricoABM/Echoes-Mobile.git
cd Echoes-Mobile
```
### 3. Configurar endereço da API

Antes de executar o aplicativo, é necessário configurar o endereço do servidor de autenticação.
O endereço da API pode ser definido no arquivo de valores constantes do sistema:
```kotlin
Const.kt
ADDRESS = "http://localhost:8080"
```
## Executando o Aplicativo

* Abra o projeto no Android Studio
* Aguarde o download das dependências do Gradle
* Execute o projeto em um emulador ou dispositivo físico
