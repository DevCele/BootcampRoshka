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


--Tabla Visitantes--
drop table if exists visitantes;

create table visitantes(
 nombre varchar(30),
 edad smallint,
 sexo char(1) default 'f',
 domicilio varchar(30),
 ciudad varchar(20) default 'Cordoba',
 telefono varchar(11),
 mail varchar(30) default 'no tiene',
 montocompra decimal (6,2)
);

insert into visitantes
 values ('Susana Molina',35,default,'Colon 123',default,null,null,59.80);
insert into visitantes
 values ('Marcos Torres',29,'m',default,'Carlos Paz',default,'marcostorres@hotmail.com',150.50);
insert into visitantes
 values ('Mariana Juarez',45,default,default,'Carlos Paz',null,default,23.90);
insert into visitantes (nombre, edad,sexo,telefono, mail)
 values ('Fabian Perez',36,'m','4556677','fabianperez@xaxamail.com');
insert into visitantes (nombre, ciudad, montocompra)  
 values ('Alejandra Gonzalez','La Falda',280.50);
insert into visitantes (nombre, edad,sexo, ciudad, mail,montocompra)
 values ('Gaston Perez',29,'m','Carlos Paz','gastonperez1@gmail.com',95.40);
insert into visitantes
 values ('Liliana Torres',40,default,'Sarmiento 876',default,default,default,85);
insert into visitantes
 values ('Gabriela Duarte',21,null,null,'Rio Tercero',default,'gabrielaltorres@hotmail.com',321.50);

 select * from visitantes;

--Queremos saber la cantidad de visitantes de cada ciudad utilizando la cláusula "group by" (4 filas devueltas)--
select ciudad, count(*)
 from visitantes
 group by ciudad;

--Queremos la cantidad visitantes con teléfono no nulo, de cada ciudad (4 filas devueltas)--
select ciudad, count(telefono)
 from visitantes
 group by ciudad;

--Necesitamos el total del monto de las compras agrupadas por sexo (3 filas)--
select sexo, sum(montocompra)
 from visitantes
 group by sexo;

--Se necesita saber el máximo y mínimo valor de compra agrupados por sexo y ciudad (6 filas)--
select sexo, ciudad, 
 max(montocompra) as MontoMaximo,
 min(montocompra) as MontoMinimo
 from visitantes
 group by sexo, ciudad;

--Calcule el promedio del valor de compra agrupados por ciudad (4 filas)--
select ciudad, avg(montocompra)
 from visitantes
 group by ciudad;

--Cuente y agrupe por ciudad sin tener en cuenta los visitantes que no tienen mail (3 filas)--
 select ciudad,
  count(*) as cantidadconmail
  from visitantes
  where mail is not null and
  mail<>'no tiene'
  group by ciudad; 

-- Tabla Clientes --
drop table if exists clientes;

create table clientes(
 codigo serial,
 nombre varchar(30) not null,
 domicilio varchar(30),
 ciudad varchar(20),
 provincia varchar(20),
 telefono varchar(11),
 primary key(codigo)
);

 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Lopez Marcos','Colon 111','Cordoba','Cordoba','null');
 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Perez Ana','San Martin 222','Cruz del Eje','Cordoba','4578585');
 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Garcia Juan','Rivadavia 333','Villa del Rosario','Cordoba','4578445');
 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Perez Luis','Sarmiento 444','Rosario','Santa Fe',null);
 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Pereyra Lucas','San Martin 555','Cruz del Eje','Cordoba','4253685');
 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Gomez Ines','San Martin 666','Santa Fe','Santa Fe','0345252525');
 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Torres Fabiola','Alem 777','Villa del Rosario','Cordoba','4554455');
 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Lopez Carlos',null,'Cruz del Eje','Cordoba',null);
 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Ramos Betina','San Martin 999','Cordoba','Cordoba','4223366');
 insert into clientes(nombre,domicilio,ciudad,provincia,telefono)
  values ('Lopez Lucas','San Martin 1010','Posadas','Misiones','0457858745');

 select * from clientes;

-- Obtenga el total de los registros agrupados por ciudad y provincia (6 filas)--
select ciudad, provincia, count(*) as total
 from clientes
 group by ciudad, provincia;

-- Obtenga el total de los registros agrupados por ciudad y provincia sin considerar los que tienen menos de 2 clientes (3 filas) --
select ciudad, provincia, count(*) as total
 from clientes
 group by ciudad, provincia
 having count(*)>1;




