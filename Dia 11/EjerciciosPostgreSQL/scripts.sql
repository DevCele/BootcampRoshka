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
drop table if exists libros;
 
 create table libros(
  codigo serial,
  titulo varchar(40) not null,
  autor varchar(20) default 'Desconocido',
  editorial varchar(20),
  precio decimal(6,2),
  primary key(codigo)
 );

 insert into libros(titulo,autor,editorial,precio)
  values('El aleph','Borges','Emece',15.90);
 insert into libros(titulo,autor,editorial,precio)
  values('Cervantes y el quijote','Borges','Paidos',null);
 insert into libros(titulo,autor,editorial,precio)
  values('Alicia en el pais de las maravillas','Lewis Carroll',null,19.90);
 insert into libros(titulo,autor,editorial,precio)
  values('Martin Fierro','Jose Hernandez','Emece',25.90);
 insert into libros (titulo,autor,precio)
  values('Antología poética','Borges',25.50);
 insert into libros (titulo,autor,precio)
  values('Java en 10 minutos','Mario Molina',45.80);
 insert into libros (titulo,autor)
  values('Martin Fierro','Jose Hernandez');
 insert into libros (titulo,autor)
  values('Aprenda PHP','Mario Molina');

 select * from libros 
  where editorial is not null;

select * from libros 
where precio not between 15 and 19.99;

select * from libros;

 insert into libros(titulo,autor,editorial,precio)
  values('El aleph','Borges','Emece',15.90);
 insert into libros(titulo,autor,editorial,precio)
  values('Cervantes y el quijote','Borges','Paidos',null);
 insert into libros(titulo,autor,editorial,precio)
  values('Alicia en el pais de las maravillas','Lewis Carroll',null,19.90);
 insert into libros(titulo,autor,editorial,precio)
  values('Matematica estas ahi','Paenza','Siglo XXI',15);
 insert into libros (titulo,precio)
  values('Antología poética',32);
 insert into libros (titulo,autor,precio)
  values('Martin Fierro','Jose Hernandez',40);
 insert into libros (titulo,autor,precio)
  values('Aprenda PHP','Mario Molina',56.50);

 select * from libros 
  where autor not in ('Borges' , 'Paenza')


select * from libros 
where autor like '%Borges%'

select * from libros 
where autor like 'M%'

SELECT * FROM libros 
WHERE autor like '%Lewis Carrol_'

select titulo, precio from libros 
	where cast(precio as varchar) like '1_.%'

select count(*)
 from libros ;

 insert into libros(titulo,autor,editorial,precio)
  values('Martin Fierro','Jose Hernandez',default,40);
 insert into libros(titulo,autor,editorial,precio)
  values('Aprenda PHP','Mario Molina','Nuevo siglo',null);
 insert into libros(titulo,autor,editorial,precio)
  values('Uno','Richard Bach','Planeta',20);

  
select count(*)
 from libros where editorial='Planeta';

select count(precio)
 from libros;

 ---Tabla Empleados --
 drop table if exists empleados;

create table empleados(
 nombre varchar(30),
 documento char(8),
 domicilio varchar(30),
 seccion varchar(20),
 sueldo decimal(6,2),
 cantidadhijos smallint,
 primary key(documento)
);

 insert into empleados
  values('Juan Perez','22333444','Colon 123','Gerencia',5000,2);
 insert into empleados
  values('Ana Acosta','23444555','Caseros 987','Secretaria',2000,0);
 insert into empleados
  values('Lucas Duarte','25666777','Sucre 235','Sistemas',4000,1);
 insert into empleados
  values('Pamela Gonzalez','26777888','Sarmiento 873','Secretaria',2200,3);
 insert into empleados
  values('Marcos Juarez','30000111','Rivadavia 801','Contaduria',3000,0);
 insert into empleados
  values('Yolanda Perez','35111222','Colon 180','Administracion',3200,1);
 insert into empleados
  values('Rodolfo Perez','35555888','Coronel Olmedo 588','Sistemas',4000,3);
 insert into empleados
  values('Martina Rodriguez','30141414','Sarmiento 1234','Administracion',3800,4);
 insert into empleados
  values('Andres Costa','28444555',default,'Secretaria',null,null);

select * from empleados

-- Muestre la cantidad de empleados usando "count" --
select count(*)
 from empleados;
 
-- Muestre la cantidad de empleados con sueldo no nulo de la sección "Secretaria" --
select count(sueldo)
 from empleados
 where seccion='Secretaria';
 
-- Muestre el sueldo más alto y el más bajo colocando un alias --
select max(sueldo) as MayorSueldo,
 min(sueldo) as MenorSueldo
 from empleados;

 
-- Muestre el valor mayor de "cantidadhijos" de los empleados "Perez" --
select max(cantidadhijos)
 from empleados
 where nombre like '%Perez%';

 
-- Muestre el promedio de sueldos de todo los empleados --
select avg(sueldo)
 from empleados;

--  Muestre el promedio de sueldos de los empleados de la sección "Secretaría" --
select avg(sueldo)
 from empleados
 where seccion='Secretaria';
 
-- Muestre el promedio de hijos de todos los empleados de "Sistemas" --
select count(cantidadhijos)
 from empleados
 where seccion='Sistemas';




