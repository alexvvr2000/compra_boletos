drop procedure if exists agregarBoleto;
create procedure agregarBoleto(
	fila varchar(10), asiento varchar(10), idEventoRequerido integer, idCompraRequerido integer
)
as $$
declare precioAsiento numeric;
	asientoDisponible boolean;
	existeEvento boolean;
	existeCompra boolean;
	compraPagada boolean;
	filaActualizado JSON;
begin
	asientoDisponible := (
		select
			capacidad.filasdisponibles -> fila -> asiento
		from capacidad
		where idEvento = idEventoRequerido 
	);
	existeCompra := (
		select exists(
			select idCompras from compra 
			where idCompras = idCompraRequerido			)
		);
	existeEvento := (
		select exists(
			select idEvento from evento 
			where idEvento = idEventoRequerido			)
		);
	if not asientoDisponible then
		RAISE EXCEPTION 'Asiento % ocupado', fila || asiento
      	USING HINT = 'Selecciona otro asiento';
	end if;
	if not existeCompra then
		RAISE EXCEPTION 'Compra con id % no existe', idCompraRequerido
      	USING HINT = 'Selecciona una compra de la base';
	end if;
	if not existeEvento then
		RAISE EXCEPTION 'Evento con id % no existe', idCompraRequerido
      	USING HINT = 'Selecciona un evento registrado';
	end if;
	compraPagada := (
		select pagado from compra
		where idCompras = idCompraRequerido
	);
	if compraPagada then
		RAISE EXCEPTION 'Ya se pago la compra con id %', idCompraRequerido
      	USING HINT = 'Realiza otra compra';
	end if;
	precioAsiento := (
		select
			capacidad.filasdisponibles -> fila -> 'precio' as precio
		from capacidad where capacidad.idEvento = idEventoRequerido
	);
	update compra
		set precioFinal = precioFinal + precioAsiento
	where idCompras = idCompraRequerido;
	update compra
		set asientosComprados = asientosComprados || (fila || asiento)
	where idCompras = idCompraRequerido;
	filaActualizado := (
		select
			filasDisponibles -> fila #- array[asiento]
			|| ('{"' || asiento || '":false}')::jsonb
		from capacidad
		where idEvento = idEventoRequerido
	);
	update capacidad
	set filasDisponibles = 
		jsonb_set(
			filasDisponibles,
			array[fila],
			filasDisponibles -> 'A' #- array['1'] || ('{"' || '1' || '":false}')::jsonb
		)
	where idEvento = idEventoRequerido;
end;
$$ language plpgsql;
call agregarBoleto('A', '1', 1, 1);
select * from capacidad;
-- SELECT jsonb_set(filasDisponibles,'{A}',filasDisponibles -> 'A' #- array['1'] || ('{"' || '1' || '":false}')::jsonb)from capacidad;