create table if not exists Usuario (
		idUsuario serial primary key,
		nombre varchar(30),
		apellidoPaterno varchar(30),
		apellidoMaterno varchar(30),
		correo varchar(30),
		claveInicioSesion varchar(30),
		esAdmin bool,
		estaBaneado bool
);
