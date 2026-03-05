  create table usuarios(

      id bigint not null auto_increment,
      nombre varchar(100) not null,
      apellido varchar(100) not null,
      nombre_usuario varchar(100) not null unique,
      email varchar(100) not null unique,
      contrasena varchar(255) not null,
      perfil varchar(100) not null,
      activo tinyint not null,

      primary key(id)

  );