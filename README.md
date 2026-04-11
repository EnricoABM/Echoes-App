# Echoes Mobile

Aplicativo Android oficial para o sistema **Echoes**, desenvolvido para monitoramento e gestão de dados de dispositivos IoT.  
O app permite que usuários autenticados acessem informações do sistema, realizem consultas e interajam com as funcionalidades da plataforma através de uma API REST.

## Funcionalidades

* Configuração do servidor – Definição do endereço do backend diretamente no app
* Registro de usuário – Cadastro com validação por código (2FA via e-mail)
* Login com 2FA – Autenticação em duas etapas
* Recuperação de senha – Solicitação de código e redefinição
* Alteração de senha – Validação da senha atual antes da troca
* Perfil do usuário – Visualização dos dados cadastrados
* Logout – Encerramento da sessão
* Armazenamento seguro – Token JWT criptografado no dispositivo

## Tecnologias utilizadas

* Kotlin
* Jetpack Compose
* Android Jetpack (ViewModel, DataStore)
* Retrofit + OkHttp
* Coroutines
* Material Design 3
* Android Keystore (AES/CBC/PKCS7)

## Arquitetura

O projeto segue o padrão **MVVM (Model-View-ViewModel)**.

Os estados de UI são representados por **sealed classes** (ex.: `LoginState`, `RegisterState`, `ForgotPasswordState`) e gerenciados com `StateFlow`.

### Estrutura de pacotes

```
com.nohana.echoes_app/
├── data/        # Armazenamento (ServerStorage, TokenStorage)
├── model/       # Modelos de domínio
├── network/     # DTOs, interceptors, Retrofit
├── security/    # Criptografia (AndroidKeyStore)
├── service/     # Serviços e validações
├── ui/theme/    # Tema e estilo
├── view/        # Telas e componentes
│   ├── components/
│   ├── screen/
│   └── state/
└── viewmodel/   # ViewModels
```

## Segurança

* Comunicação via HTTPS (quando disponível)
* Token JWT armazenado de forma criptografada
* Uso de Android Keystore para gerenciamento de chave
* Senhas não são armazenadas localmente
* Validação de senha com critérios mínimos de segurança

## Como executar o projeto

### 1. Pré-requisitos

* Android Studio (versão recente)
* Android SDK (API 30+)
* Emulador ou dispositivo físico

### 2. Clonar o repositório

```bash
git clone https://github.com/EnricoABM/Echoes-Mobile.git
cd Echoes-Mobile
```

### 3. Configurar o servidor

O aplicativo não possui endereço fixo.

Na inicialização:
* acessar a opção **IP Config**
* informar o endereço do backend (ex.: 192.168.0.100)

O app irá utilizar automaticamente:
```
https://<ip>:8443
```

Caso esteja em ambiente de desenvolvimento:
```
http://<ip>:8080
```

Pode ser necessário permitir HTTP no arquivo:
```
network_security_config.xml
```

### 4. Executar

* Abrir o projeto no Android Studio
* Aguardar sincronização do Gradle
* Executar no dispositivo/emulador

## Endpoints utilizados

```
POST   /api/auth/register
POST   /api/auth/register/2fa
POST   /api/auth/login
POST   /api/auth/login/2fa
GET    /api/auth/validate-token
GET    /api/auth/logout
GET    /api/users/me

POST   /api/password/forgot
POST   /api/password/reset
POST   /api/password/validate
POST   /api/password/change
```

Endpoints protegidos utilizam:

```
Authorization: Bearer <token>
```

## Observações

* O app depende diretamente do servidor Echoes
* O backend deve estar em execução para uso completo
* Em produção, utilizar sempre HTTPS

## Licença

Projeto de uso acadêmico.
