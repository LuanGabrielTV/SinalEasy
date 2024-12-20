# SinalEasy

## Inicialização do projeto

Faça o clone do repositório

        git clone git@github.com:LuanGabrielTV/SinalEasy.git

### Utilizando Docker

#### Pré-requisitos

Certifique-se de ter o Docker e Docker Compose instalados na sua máquina.

A aplicação utiliza docker-compose para gerenciar as imagens geradas pelo docker. Portanto, basta ir na raíz do projeto e executar o arquivo ./start.sh, caso esteja utilizando sistema operacional baseado em Unix.

Caso esteja em algum outro sistema operacional, ainda na raiz do projeto, execute os seguintes comandos no terminal:

    docker-compose down
    docker build -t backend-sinaleasy:latest ./sinaleasy-back
    docker-compose up --build --force-recreate --remove-orphans

Acesse localmente através do endpoint http://localhost:4200


#### Passos para executar

1. Navegue até o diretório do backend:

   cd sinaleasy-back


### Sem a utilização do docker

Para executar o backend do projeto, siga os passos abaixo:

#### Backend

##### Pré-requisitos

- Certifique-se de ter o Java 21 instalado.
- O banco de dados utilizado é o PostgreSQL hospedado no provedor Neon, então você não precisa ter o PostgreSQL instalado localmente (é necessário o arquivo .env).


##### Passos para executar

1.Navegue até o diretório do backend:

    git clone git@github.com:LuanGabrielTV/SinalEasy.git
    cd sinaleasy-back
   
2. **Execute o arquivo:**

    SinaleasyBackApplication
 
#### Frontend
 
##### Pré-requisitos
 
Para executar o frontend, é preciso ter o Angular instalado. A versão do node em que este trabalho foi desenvolvido é a 23.10. 

##### Passos para executar

1. Entre no diretório sinaleasy-front e instale as dependências:

    npm install

2. Inicialize a interface:

    ng serve

3. Aguarde o processo. Ao fim, acesse o link http://localhost:4200 no navegador e acesse o Sinaleasy.



