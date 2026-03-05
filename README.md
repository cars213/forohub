# forohub
Foro educativo desarrollado en java como desafio del curso Java y Spring Framework de alura, donde se puede registrar y resolver tus dudas o resolver las dudas de otros usuarios

<img width="522" height="383" alt="image" src="https://github.com/user-attachments/assets/a8317d72-36b5-43f9-8f56-1c6bc58214d5" />

## Descripción del Proyecto
Proyecto finalizado, es un foro educativo el cual permite crear, modificar, listar y eliminar usuario, a su vez cada usuario puede crear, listar, actualizar y eliminar topicos de cualquier duda que tengan, posteriomente los administradores y profesores podran crear, listar, actualizar y eliminar cursos por ultimo se podran crear altualizar y eliminar respuestas a diferentes topicos, dependiendo de la respuesta, el dueño del topico puede seleccionar que respuesta considera una solucion y marcarla en base a la id, todo esto almacenado en base de datos mySQL

## :hammer:Funcionalidades del proyecto
- `Funcionalidad 1`: Sistema CRUD para el usuario.
- `Funcionalidad 2`: Sistema de inicio de sesion.
- `Funcionalidad 3`: Sistema CRUD para el topico.
- `Funcionalidad 4`: Sistema CRUD para los cursos.
- `Funcionalidad 5`: Sistema CRUD para las repuestas.
- `Funcionalidad 6`: Sistema de seguridad para que los topicos y las respuestas solo puedan ser modificadas por el autor.
- `Funcionalidad 7`: Sistema de asignacion de id para indicar el autor de los topicos y las respuestas.
- `Funcionalidad 8`: Validacion que se asegura que solo usuarios con perfil de administrador o profesor puedan modificar los cursos.
- `Funcionalidad 9`: Almacenamiento de la informacion en base de datos.
- 
## Acceso al proyecto

Para descargar el proyecto se realiza click sobre el recuadro rojo (code).

Posteriomente se hace click en el recuadro verde (Download zip) para descargar la carpeta comprimida.
<img width="577" height="474" alt="image" src="https://github.com/user-attachments/assets/d4a0b15a-949a-4bd5-bcf6-9bd99a9eae01" />

Se descomprime la carpeta y se abre en un editor de codigo JAVA (recomendable Intellij), con esto puede ejecutar forohub.

## Abre y ejecuta el proyecto.
Ya con el proyecto instalado, se abre con un editor de codigo JAVA (recomendable Intellij), posteriomente para una visualizacion mejor acceder al siguiente enlace 
cuando el proyecto este en funcionamiento http://localhost:8080/swagger-ui/index.html#/, como primera medida se registra el usuario para poder acceder a todas las funciones del foro
para realizar pruebas el foro permite crear la cuenta con el perfil que desees administrados, profesor o estudiante, una actualizacion eliminaria esta opcion.

<img width="926" height="402" alt="image" src="https://github.com/user-attachments/assets/9c256083-4c45-452c-866f-fb509e077f2a" />

Un ejemplo en Json de como se debe registrar el usuario.

<img width="311" height="183" alt="image" src="https://github.com/user-attachments/assets/2f917e38-9644-460c-bed9-4eadce81dd1a" />

Posteriomente se muestran las posibles funciones que puede realizar con el usuario, la mayoria no requieren acceso ya que son funciones de modificacion de contraseña, modificacion de correo
etc, modificaciones que pueden ocurrir por olvido, para observar los usuarios si debe autenticarse

<img width="933" height="304" alt="image" src="https://github.com/user-attachments/assets/f49bc2bc-9b56-4e57-8c28-fdc62d1f9a20" />

El apartado de los cursos puede crear, actualizar, leer y eliminar topicos, solo se puede actualizar y eliminar los topicos de su usuario, sin importar el perfil.

<img width="933" height="253" alt="image" src="https://github.com/user-attachments/assets/0e45ec2a-38a0-47fb-9104-5d1099dd0161" />

El apartado de los cursos, solo los usuarios con perfil de profesor o administrador pueden crear, actualizar y eliminar un curso.

<img width="931" height="262" alt="image" src="https://github.com/user-attachments/assets/4d9987f8-b8f9-4d86-8535-74ba92bf6703" />

Para registrar un curso solo debe poner un nombre y la categoria, por el momento solo esta disponible (PROGRAMACION, FRONTEND, DATASCIENCE, IA, DEVOPS)

<img width="301" height="125" alt="image" src="https://github.com/user-attachments/assets/6b373999-150a-4849-a487-df526f9b2896" />

Por ultimo el apartado de las respuestas, en esta puede crear, actualizar, leer y eliminar las respuestas, pero solo las de su autoria.

<img width="929" height="246" alt="image" src="https://github.com/user-attachments/assets/3517f4cf-4eba-43b7-912c-1ee99a455e1e" />


## Tecnologias utilizadas
- JAVA
- Maven
- Spring Framework
- JPA
- JWT
- MySQL
## Personas Contribuyentes
Equipo de Alura latam.
## Autor
- Carlos Andres Rendon.

