drop table if exists Usuario cascade;
drop table if exists MetodoPago cascade;
drop table if exists Evento cascade;
drop table if exists capacidad;
drop table if exists compra;
drop type if exists multinacional;
create table Usuario (
		idUsuario serial primary key,
		nombre varchar(30),
		apellidoPaterno varchar(30),
		apellidoMaterno varchar(30),
		correo varchar(30) unique,
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
	fecha timestamp,
	estaCancelado bool default FALSE
);
create type multinacional as enum ('visa','mastercard','americanExpress');
create table capacidad (
	idEvento integer primary key references Evento(idEvento) on delete cascade,
	filasDisponibles json
);
insert into evento(nombre, lugar, fecha)
values ('Evento', 'Arena monterrey', now());
insert into capacidad(idevento, filasDisponibles)
values (
	1, 
	'{"A": {"1":true,"2":true,"3":true,"precio": 200}, "B":{"1":false,"2":true,"3":true, "precio": 500}}'
);
create table MetodoPago (
	idMetodoPago serial primary key,
	idUsuario integer references Usuario(idUsuario) on delete cascade,
	cuenta varchar(30) unique,
	fechaVencimiento char(5),
	tipoCuenta multinacional
);
insert into MetodoPago (idUsuario, cuenta, fechaVencimiento, tipoCuenta)
values(1, '2144563542432', '02/29', 'visa');
insert into MetodoPago (idUsuario, cuenta, fechaVencimiento, tipoCuenta)
values(1, '3242343213213', '02/29', 'mastercard');
select * from usuario;
create table compra (
	idCompras serial primary key,
	idUsuario integer references Usuario(idUsuario) on delete cascade,
	idEvento integer references Evento(idEvento) on delete cascade,
	idMetodoPago integer references MetodoPago(idMetodoPago) on delete cascade,
	asientosComprados JSON,
	precioFinal numeric,
	pagado boolean default false
);
insert into 
	compra (idusuario, idevento, idmetodopago)
values (1,1,1);