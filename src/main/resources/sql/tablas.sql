drop table if exists Usuario cascade;
drop table if exists MetodoPago cascade;
drop table if exists Evento cascade;
drop table if exists capacidad;
drop table if exists compras;
drop type if exists multinacional;
create table Usuario (
		idUsuario serial primary key,
		nombre varchar(30),
		apellidoPaterno varchar(30),
		apellidoMaterno varchar(30),
		correo varchar(30),
		claveInicioSesion varchar(30),
		esAdmin bool default FALSE
);
insert into usuario (nombre, apellidoPaterno, apellidoMaterno, correo, claveInicioSesion, esAdmin)
values 
	('Erika', 'Cuevas', 'Lucio', 'erika12@hotmail.com', '234', false),
	('Alejandro', 'Valenzuela', 'Rivera', 'ale@gmail.com', '12345', true);
create table Evento (
	idEvento serial primary key,
	nombre varchar(30),
	lugar varchar(30),
	fecha date,
	hora time,
	estaCancelado bool default FALSE
);
create type multinacional as enum ('visa','mastercard');
create table MetodoPago (
	idMetodoPago serial primary key,
	idUsuario integer references Usuario(idUsuario) on delete cascade,
	cuenta integer unique,
	fechaVencimiento date,
	tipoCuenta multinacional
);
create table capacidad (
	idEvento integer primary key references Evento(idEvento) on delete cascade,
	filaA json,
	filaB json,
	filaC json,
	filaD json,
	precioAsiento money
);
create table compras (
	idCompras serial primary key,
	idUsuario integer references Usuario(idUsuario) on delete cascade,
	idEvento integer references Evento(idEvento) on delete cascade,
	idMetodoPago integer references MetodoPago(idMetodoPago) on delete cascade,
	asientosComprados varchar(10)[]
);