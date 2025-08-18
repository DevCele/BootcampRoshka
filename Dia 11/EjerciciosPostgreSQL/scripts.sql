-- Tabla de Visitantes--
drop table if exists visitantes;

create table visitantes (
 nombre character varying(30),
 edad integer,
 sexo character(1),
 domicilio character varying(30),
 ciudad character varying(20),
 telefono character varying(11)
 );

 insert into visitantes (nombre, edad, sexo, domicilio, ciudad, telefono)
  values ('Marcela Morales', 43, 'f', 'Colon 456', 'Cordoba', 4567890);

 select * from visitantes;


--Tabla Asistencia --
drop table if exists asistencia;

create table asistencia(
  dni char(8),
  fechahora timestamp,
  primary key(dni)
);

insert into asistencia (dni, fechahora)
 values ('11111111', '2008/12/31 13:00:59');

select * from asistencia;

set datestyle to 'European';

insert into asistencia (dni, fechahora)
 values('22222222', '21/12/2018 13:00:00')

 --Tabla Usuarios --
 drop table if exists usuarios;

create table usuarios (
 nombre varchar(20),
 clave varchar(10),
 primary key(nombre)
);

insert into usuarios (nombre, clave)
 values ('juanperez', 'Boca');
insert into usuarios (nombre, clave)
 values ('raulgarcia', 'River');

select * from usuarios;

insert into usuarios (nombre, clave)
 values (null, 'payaso');

delete from usuarios where nombre = '';


update usuarios set nombre='juanperez'
 where nombre='raulgarcial';

--Tabla Libros --
