-- tipos de datos para roles de usuario
create type rol_usuario as enum ('admin', 'autor', 'usuario');

-- tipos de datos para estado de posteos
create type estado_posteo as enum ('borrador', 'publicado', 'archivado');

-- tipos de datos para estado de comentarios
create type estado_comentario as enum ('pendiente', 'aprobado', 'rechazado');

-- tabla de usuarios
create table usuarios (
    id_usuario serial primary key,
    nombre_usuario varchar(50) not null unique,
    email varchar(100) not null unique,
    contraseña varchar(255) not null,
    nombre_completo varchar(100) not null,
    fecha_registro timestamp default current_timestamp,
    activo boolean default true,
    rol rol_usuario default 'usuario'
);

-- tabla de posteos
create table posteos (
    id_posteo serial primary key,
    id_usuario integer not null,
    titulo varchar(200) not null,
    contenido text not null,
    fecha_creacion timestamp default current_timestamp,
    fecha_actualizacion timestamp default current_timestamp,
    estado estado_posteo default 'borrador',
    categoria varchar(50),
    tags varchar(500),
    
    foreign key (id_usuario) references usuarios(id_usuario) on delete cascade
);

-- tabla de comentarios
create table comentarios (
    id_comentario serial primary key,
    id_posteo integer not null,
    id_usuario integer not null,
    id_comentario_padre integer null,
    contenido text not null,
    fecha_creacion timestamp default current_timestamp,
    fecha_actualizacion timestamp default current_timestamp,
    estado estado_comentario default 'pendiente',
    
    foreign key (id_posteo) references posteos(id_posteo) on delete cascade,
    foreign key (id_usuario) references usuarios(id_usuario) on delete cascade,
    foreign key (id_comentario_padre) references comentarios(id_comentario) on delete cascade
);

