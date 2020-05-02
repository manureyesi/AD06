# Tarefa AD06

Nesta tarefa vamos utilizar MongoDB para crear unha aplicación similar a twitter pero con varias limitacións.
Descripción do problema

## A aplicación terá as seguintes características:

- Cando se inicia a aplicación teremos un menú con dúas opcións: rexistrarse e loguearse.
- No __rexistro__: Para cada usuario gardaremos o nome completo, o username (non poden existir dous iguais), e o seu contrasinal. O contrasinal neste caso non temos que gardalo cun hash (nun caso real si que faría falta, isto facémolo para facelo máis sinxelo.)
- No __login__: Teremos que comprobar se o nome de usuario e contrasinal son os correctos. Se son os correctos pasaremos ao menú inicio.
- O menú inicio terá as seguintes opcións:

  - Ver tódalas mensaxes.
  - Ver mensaxes de usuarios que sigo.
  - Buscar por hashtag. Un hashtag ten a seguinte forma “#palabra”.
  - Escribir unha mensaxe.
  - Buscar usuarios.

- __Ver tódalas mensaxes__: mostrará todalas mensaxes que hai na aplicación. Primeiro mostraranse as máis recentes. Só se mostrarán de 5 en 5. Polo tanto hai que paxinar os resultados para poder ir navegando entre mensaxes. As mensaxes deben mostrar o nome e username de quen creou a mensaxe, o contido da mensaxe e a data de creación da mensaxe.
- __Ver mensaxes de usuarios que sigo__: mostrará todalas mensaxes dos usuarios que sigo. Mostraranse de xeito similar a “ver tódalas mensaxes”.
- __Buscar por hashtag__: permitirá introducir un hastag e devolverá tódolas mensaxes con ese hashtag. Mostraranse de xeito similar a “ver tódalas mensaxes”.
- __Escribir unha mensaxe__: permitirá escribir unha mensaxe. Esta non ten límite de caracteres. Debemos gardar o usuario que a creou, o contido da mensaxe e a data de creación.
- __Buscar usuarios__: o usuario introducirá un username e buscaranse tódolos usuarios que coincidan en parte con ese username. Por exemplo se buscamos por “manu” poderían aparecer: “manuel”, “emmanuel”, etc. Para iso faranse consultas con expresións regulares. Cos resultados, debidamente paxinados, poderemos elixir un usuario para seguir.

Para non vos complicar co modelado de datos e poder facer unha posta en común utilizaremos o seguinte modelo de datos.

__Colección usuario:__

```
{
    "nome":"Manuel Varela",
    "username":"manu",
    "password":"abc123.",
    "follows":["username1","username2","username3"]
}
```

__Colección mensaxe:__

```
{
    "text":"Este é un exemplo de miniTwitter #MiniTwitter #AccesoADatos",
    "user":{
        "nome": "Manuel Varela",
        "username": "manu"
    },
    "date":"2016-05-18T16:00:00Z",
    "hashtags":["MiniTwitter","AccesoADatos"]
}
```

Creade as coleccións cun cliente de MongoDB como Robo3T. Non o fagades mediante código.

Para a conexión a base de datos imos utilizar un arquivo de configuración como o seguinte:

```
{
    "address":"192.168.56.1",
    "port":"27017",
    "dbname":"minitwitter",
    "username":"accesoADatos",
    "password":"4cc3s04d4t0s"
}
```

Para as vosas probas na máquina que vos pasei non é necesario utilizar username e password. Isto só o imos utilizar para poder probar todos a nosa app sobre unha mesma base de datos. Unha vez que teñades a vosa aplicación acabada e como todos seguimos o mesmo modelo de datos poderemos utilizar a mesma base de datos para probar. Deixareivos no foro as credenciais para a conexión a esta base de datos. __Para a corrección da tarefa utilizarase esta base de datos online que vos paso.__
