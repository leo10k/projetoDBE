# projetoDBE

Uma Api para sistema de um site de noticias onde os usuarios podem criar e compartilhar conteudo.

## Endpoints

- Postar conteudo
    - [postar](#postar-conteudos)
    - [apagar](#apagar-post)
    - [alterar](#alterar-post)
    - [mostrar detalhes](#detalhes-do-post)

---

### postar Conteudos

`POST` /projetoDBE/api/conteudo

**Campos da Rquisicao**

| campo | tipo | obrigatorio | descricao
|-------|------|:------------:|---
|usuario| texto | sim | mostra o nome do usuario
|jogo| texto | sim | escolher o jogo no qual o usuario ira fazer um post
|titulo| texto | nao | titulo do post
|descricao| texto | sim | descricao do post
|img/git| imagem/video/gif | nao | um video, imagem ou gif relacionado ao post

**Exemplo de corpo de requisicao**

```js
{   
    "usuario": "Leo"
    "jogo": "Counter-Strike: Global Offensive",
    "titulo": "Meu priemiro Ace",
    "descricao": "Se liga nesse Clutch!",
    "img/git": "vid.mp4"
}
```

**Codigos de Respotas**

| codigo | descricao
|-|-
| 201 | conteudo criado com sucesso
| 400 | campos invalidos

----

### Detalhes do Post

`GET` /projetoDBE/api/post/{id}

**Exemplo corpo de resposta**

```js
{
    "usuario": {
        "usuario_id": 1,
        "nome": "Leo"
    },
    "jogo": {
        "jogo_id": 2,
        "nome": "Counter-Strike: Global Offensive"
    },
    "titulo": "Meu priemiro Ace",
    "descricao": "Se liga nesse Clutch!",
    "img/git": "vid.mp4"
}
```

**Codigo de Respostas**

| codigo | descricao
|-|-
| 201 | conteudo criado com sucesso
| 404 | nao existe conteudo com o id informado

### Apagar Post

`DELETE` /projetoDBE/api/post/{id}

**Codigo de Respostas**

| codigo | descricao
|-|-
| 200 | conteudo excluido
| 404 | nao existe conteudo com o id informado

### Alterar Post

`PUT` /projetoDBE/api/post/{id}

**Exemplo corpo de resposta**

```js
{   
    "usuario": "Pedro"
    "jogo": "Elden Ring",
    "titulo": "",
    "descricao": "Matei o boss mais dificil sem tomar dano",
    "img/git": "elden_ring.gif"
}
```

**Codigo de Respostas**

| codigo | descricao
|-|-
| 201 | conteudo alterado com sucesso
| 400 | campos invalidos
| 404 | nao existe conteudo com o id informado