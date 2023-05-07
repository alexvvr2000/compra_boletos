drop table if exists Usuario;
create table Usuario (
		idUsuario serial primary key,
		nombre varchar(30),
		apellidoPaterno varchar(30),
		apellidoMaterno varchar(30),
		correo varchar(30),
		claveInicioSesion varchar(30),
		esAdmin bool,
		estaBaneado bool default FALSE
);
drop table if exists Evento;
create table Evento (
	idEvento serial primary key,
	nombre varchar(30),
	lugar varchar(30),
	fecha date,
	hora time,
	estaCancelado bool default FALSE
);