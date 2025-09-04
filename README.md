
# Configuracion de dos APIS en Spring WebFlux + NoSQL (MongoDB)

Configuracion de dos APIS de la pagina RAPID API para su uso con MongoDB en Spring WebFlux.


![images](https://miro.medium.com/v2/resize:fit:1400/1*uIRAO6kSOd-yMYVxpaDSXw.png)
## API Disney Plus Top Movies and TV Shows

La API llamada "Disney Plus Top Movies and TV Shows" tiene como funcionalidad buscar datos en base a los datos existentes para ser consultas en API,es decir, que si hay una pelicula como por ejemplo "Star Wars" en MongoDB,se podra hacer consulta en RAPID API para mostrar peliculas y series basadas en el nombre, y si en caso de que no se mostrara resultados y mostrara un mensaje de error de que no se encontró la película en MongoDB.

![images](https://cdn.mos.cms.futurecdn.net/2zimuzZc9XoW6aRRZMSQh6-1200-80.jpg)

#### Listar Datos de la base datos en MongoDB

```http
  GET http://localhost:8085/v1/api/RapidAPI/Disney/GetAll
```

#### Buscar peliculas en Mediante el Nombre en MongoDB

```http
  GET http://localhost:8085/v1/api/RapidAPI/Disney/search/{name}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | Nombre de la pelicula o serie (OBLIGATORIO) |

#### Si no encuentra datos en MongoDB

```javascript
{
  "message": "No se encontró la película en MongoDB"
}
```

#### Buscar peliculas y series referentes al nombre de la pelicula de MongoDB con el API

```http
  GET http://localhost:8085/v1/api/RapidAPI/Disney/searchFull/{name}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | Nombre de la pelicula o serie (OBLIGATORIO) |


#### Si no encuentra la pelicula en MongoDB antes de hacer la consulta en el APi

```javascript
{
  "message": "No se encontró la película en MongoDB"
}
```
#### Crear datos para la base de datos en MongoDB

```http
  POST http://localhost:8085/v1/api/RapidAPI/Disney/save
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `id` | `string` | ID del dato (OPCIONAL) |
| `name` | `string` | Nombre de la pelicula o serie (OBLIGATORIO) |

## API Netflix

La API llamada "Netflix" tiene como funcionalidad mostrar los idiomas disponibles que hay en paltaforma, en la cual el usuario, puede ver, crear, eliminar y verficar, si un idioma esta disponible en API en base a la base de datos MongoDB.

![images](https://images.ctfassets.net/4cd45et68cgf/4nBnsuPq03diC5eHXnQYx/d48a4664cdc48b6065b0be2d0c7bc388/Netflix-Logo.jpg)

#### Listar Datos que hay en la API de Netflix

```http
  GET http://localhost:8085/v1/api/netflix/languages/api
```

#### Buscar un idioma en base al codigo de lenguaje en MongoDB

```http
  GET http://localhost:8085/v1/api/netflix/languages/db/{code}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `code` | `string` | codigo de lenguaje (OBLIGATORIO) |

#### Si no encuentra el lenguaje en MongoDB

```javascript
{
  "message": "No se encontró el idioma en MongoDB"
}
```
#### Listar Datos que hay en MongoDB

```http
  GET http://localhost:8085/v1/api/netflix/languages/db/GetAll
```

#### crear datos para la base de datos en MongoDB
```http
  POST http://localhost:8085/v1/api/netflix/languages/db/save
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `code` | `string` | codigo de lenguaje (OBLIGATORIO) |
| `name` | `string` | nombre del lenguaje (OBLIGATORIO) |

#### Borrar datos con el codigo de lenguaje en MongoDB
```http
 DELETE http://localhost:8085/v1/api/netflix/languages/db/delete/{code}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `code` | `string` | codigo de lenguaje (OBLIGATORIO) |

## MAVEN DEPENDENCIAS

- **Spring WebFlux** (`spring-boot-starter-webflux`)  
  Para construir aplicaciones reactivas con Spring WebFlux (basado en Project Reactor).

- **Spring Data MongoDB Reactive** (`spring-boot-starter-data-mongodb-reactive`)  
  Para conectarse y trabajar de forma reactiva con MongoDB.

- **SpringDoc OpenAPI / Swagger UI** (`springdoc-openapi-starter-webflux-ui`)  
  Para generar documentación automática OpenAPI/Swagger de tu API reactiva.

- **Lombok** (`lombok`)  
  Reduce código boilerplate (getters, setters, constructores, etc.).

- **Reactor Test** (`reactor-test`)  
  Librería para tests unitarios en aplicaciones reactivas con Project Reactor.

## Dependencias

```javascript
<dependencies>
    <!-- Spring WebFlux -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-webflux</artifactId>
    </dependency>

    <!-- MongoDB Reactivo -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb-reactive</artifactId>
    </dependency>

    <!-- Swagger / OpenAPI -->
    <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webflux-ui</artifactId>
        <version>2.0.2</version>
    </dependency>

    <!-- Lombok (opcional) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>

    <!-- Testing con Reactor -->
    <dependency>
        <groupId>io.projectreactor</groupId>
        <artifactId>reactor-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```
## LINKS API
- [API NETFLIX](https://rapidapi.com/Glavier/api/netflix54)
- [API DISNET PLUS](https://rapidapi.com/apirobots/api/disney-plus-top-movies-and-tv-shows-api-by-apirobots)
