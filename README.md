# Hardware Control API

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black)

## Índice

- <a href="#-Resumo">Sobre</a>
- <a href="#Licença">Licença</a>
- <a href="#-Instalacao">Instalação</a>
- <a href="#-Uso">Uso</a>
- <a href="#-PrincipalFunc">Funcionalidades Principais</a>
- <a href="#-EndPoints">Endpoints</a>
- <a href="#-Auth">Autenticação</a>
- <a href="#-DB">Banco de Dados</a>
- <a href="#-Diagrama">Diagrama</a>
- <a href="#-Desenvolvedores">Desenvolvedores</a>



## Sobre

Esta é uma API  desenvolvida utilizando Spring Security 6+ que realiza a autenticação de usuários retornando um token JWT para interações. Foram adicionados diferentes endpoints que retornam o token como string, header ou cookie
tanto o token quanto as informações do usuário.
+

## Licença


 [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

## Instalação
 1. Clone o repositório

        https://github.com/Allamymp/spring-security-jwt

 2. Tenha o Docker instalado na sua máquina : (https://docs.docker.com/manuals/)


## Uso

1. Inicie a aplicação via docker-compose no arquivo Compose.yaml
2. A api estará disponível na porta 8080 e o banco de dados Postgre na porta 5432

## Funcionalidades Principais 

- CR de usuário
- Autenticação e geração de token


## API Endpoints

A API disponibiliza os seguintes EndPoints:

   
    
    Tipo GET:
        - /all - retorna todos os usuarios.
        - /find - retorna um json com informações do usuario via parametro na URI.
        - /find-cookie - retorna um cookie com informações do usuario via parametro na URI.
        - /find-header - retorna  no header as informações do usuário via parametro na URI.
    
    Tipo POST:
        - /create - cria um usuario
        - /authenticate - autentica via basic auth um usuario e retorna um token. 
        - /authenticate-cookie - autentica via basic auth um usuario e retorna um token via cookie.
        - /authenticate-header - autentica via basic auth um usuario e retorna um token via header.
     

## Banco de Dados

O projeto utiliza [PostgresSQL](https://www.postgresql.org/) como banco de dados gerado dentro do contêiner não sendo necessária a instalação manual.

## Diagrama

![diagrama](https://github.com/Allamymp/spring-security-jwt/assets/61341833/289a2a97-7463-4062-951f-2b4a35313503)



## Desenvolvedores

Desenvolvedores que contribuiram com esta aplicação:

- Allamy Monteiro Pereira: [@Allamymp](https://github.com/Allamymp)
    [linkedin]()
 
 
 

