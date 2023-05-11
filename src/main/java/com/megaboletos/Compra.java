package com.megaboletos;
import com.megaboletos.usuarios.ClassBuilder;
import com.megaboletos.usuarios.Cliente;
import com.megaboletos.usuarios.Evento;

import java.sql.*;
import java.util.*;
public class Compra {
    private int idCliente = 0;
    private int idEvento = 0;
    private int idCompra = 0;
    private int idMetodoPago = 0;
    private int precioFinal = 0;
    private boolean pagado = false;
    List<String> asientos = new ArrayList<String>();
    private Connection conexion = null;
    private Compra(Builder nuevaCompra) throws Exception {
        PreparedStatement query = nuevaCompra.conexion.prepareStatement(
            "insert into " +
            "compra (idusuario, idevento, idmetodopago) " +
            "values (?,?,?) returning idcompras;"
        );
        query.setInt(1, nuevaCompra.clientePorComprar.getIdUsuario());
        query.setInt(2, nuevaCompra.idEvento);
        query.setInt(3, nuevaCompra.idMetodoPago);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        this.conexion = nuevaCompra.conexion;
        this.idCompra = resultado.getInt("idcompras");
        this.idCliente = nuevaCompra.clientePorComprar.getIdUsuario();
        this.idEvento = nuevaCompra.idEvento;
        this.idMetodoPago = nuevaCompra.idMetodoPago;
        this.precioFinal = nuevaCompra.precioFinal;
        this.pagado = false;
        nuevaCompra.conexion.commit();
    }
    public Compra(Connection conexion, Cliente clienteUsado, int idCompra) throws Exception{
        if(!clienteUsado.existeCompra(idCompra)) throw new Exception("Compra no existe en base");
        PreparedStatement query = conexion.prepareStatement(
            "select " +
                "idusuario, idevento, idmetodopago, " +
                "asientoscomprados, preciofinal, pagado " +
            "from compra " +
            "where idcompras = ?;"
        );
        query.setInt(1, idCompra);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        this.idCompra = idCompra;
        this.idCliente = clienteUsado.getIdUsuario();
        this.idEvento = resultado.getInt("idevento");
        this.idMetodoPago = resultado.getInt("idmetodopago");
        Array arregloValores = resultado.getArray("asientoscomprados");
        this.precioFinal = resultado.getInt("preciofinal");
        this.pagado = resultado.getBoolean("pagado");
        Collections.addAll(this.asientos, (String[])arregloValores.getArray());
    }
    public int getIdCliente() {
        return this.idCliente;
    }
    public int getIdEvento() {
        return this.idEvento;
    }
    public int getIdCompra() {
        return this.idCompra;
    }
    public int getIdMetodoPago() {
        return this.idMetodoPago;
    }
    public int getPrecioFinal() {
        return this.precioFinal;
    }
    public List<String> getAsientos() {
        return Collections.unmodifiableList(this.asientos);
    }
    public boolean estaPagado() {
        return this.pagado;
    }
    public static class Builder implements ClassBuilder<Compra> {
        private Cliente clientePorComprar = null;
        private int idEvento = 0;
        private int idMetodoPago = 0;
        private int precioFinal = 0;
        private Connection conexion = null;
        public Builder(Connection conexion, Cliente clientePorComprar) throws Exception{
            if(clientePorComprar.estaCerradaSesion()) throw new Exception("Sesion cerrada");
            this.conexion = conexion;
            this.clientePorComprar = clientePorComprar;
        }
        public Builder agregarEvento(int idEvento) throws Exception{
            if(!Evento.existeEvento(this.conexion, idEvento)) throw new Exception("Evento no existe");
            this.idEvento = idEvento;
            return this;
        }
        public Builder metodoPagoUsado(int idMetodoPago) throws Exception{
            if(!this.clientePorComprar.tieneMetodoPago(idMetodoPago))
                throw new Exception("Metodo de pago no existe");
            this.idMetodoPago = idMetodoPago;
            return this;
        }
        @Override
        public Compra crear() throws Exception{
            if(!this.camposValidos()) throw new Exception("Campos faltantes o sin formato");
            return new Compra(this);
        }
        @Override
        public boolean camposValidos() {
            boolean idEventoAgregado = this.idEvento != 0;
            boolean idMetodoPagoAgregado = this.idMetodoPago != 0;
            return
                idEventoAgregado &&
                idMetodoPagoAgregado;
        }
    }
    public boolean pagar(int CVV) throws Exception{
        return true;
    }
    public boolean agregarAsiento(String fila, int asiento) throws Exception{
        if(this.pagado) throw new Exception("Pases pagados crea una nueva compra");
        Evento eventoRequerido = new Evento(this.conexion, this.idEvento);
        if(eventoRequerido.eventoCancelado())
            throw new Exception("Evento cancelado");
        if(!eventoRequerido.existeAsiento(fila, asiento))
            throw new Exception("Asientos nuevos invalidos y no existen en base");
        if(!eventoRequerido.estaDisponible(fila, asiento))
            throw new Exception("Asiento ocupado seleccione otro");
        PreparedStatement asientoABase = this.conexion.prepareStatement(
            String.format(
                "update capacidad " +
                "set filasDisponibles = " +
                "jsonb_set( " +
                "filasDisponibles, " +
                "array['%s'], " +
                "filasDisponibles -> '%s' #- array['%d'] || ('{\"%d\":false}')::jsonb) " +
                "where idEvento = %d;"
                , fila, fila, asiento, asiento, this.idEvento
            )
        );
        boolean asientosAfectados = asientoABase.executeUpdate() == 1;
        this.conexion.commit();
        PreparedStatement agregarALista = this.conexion.prepareStatement(
            "update compra " +
            "set asientosComprados = array_append(asientosComprados, ?) " +
            "where idCompras = ?;"
        );
        final String nuevoAsiento = String.format("%s%d", fila, asiento);
        agregarALista.setString(1, nuevoAsiento);
        agregarALista.setInt(2, this.idCompra);
        boolean camposAfectados = agregarALista.executeUpdate() == 1;
        this.conexion.commit();
        Integer precioAsiento = new Evento(this.conexion, this.idEvento).precioAsiento(fila);
        this.precioFinal += precioAsiento;
        PreparedStatement actualizarPrecio = this.conexion.prepareStatement(
            "update compra " +
            "set preciofinal = ? " +
            "where idCompras = ?;"
        );
        actualizarPrecio.setInt(1, this.precioFinal);
        actualizarPrecio.setInt(2, this.idCompra);
        boolean precioCambiado = actualizarPrecio.executeUpdate() == 1;
        this.conexion.commit();
        return asientosAfectados && camposAfectados && precioCambiado;
    }
    public static class ComprasIterator implements Iterator<Compra> {
        private int cantidadValores = 0;
        private int valorActual = 0;
        private ResultSet clavesCrudas = null;
        private Cliente cliente = null;
        private Connection conexion = null;
        public ComprasIterator(Connection conexion, Cliente cliente) throws Exception {
            PreparedStatement query = conexion.prepareStatement(
                "select " +
                "cast(count(idCompras) as integer) as cantidad " +
                "from compra where idUsuario = ?;"
            );
            query.setInt(1, cliente.getIdUsuario());
            ResultSet resultado = query.executeQuery();
            resultado.next();
            this.cantidadValores = resultado.getInt("cantidad");
            if(this.cantidadValores == 0) throw new Exception("No hay clientes en base");
            PreparedStatement queryMetodosPago = conexion.prepareStatement(
                "select " +
                "idCompras " +
                "from compra where idUsuario = ?;"
            );
            queryMetodosPago.setInt(1, cliente.getIdUsuario());
            this.clavesCrudas = queryMetodosPago.executeQuery();
            this.cliente = cliente;
            this.conexion = conexion;
        }
        @Override
        public boolean hasNext() {
            return this.cantidadValores != this.valorActual;
        }
        @Override
        public Compra next() {
            try{
                this.clavesCrudas.next();
                int idActual = this.clavesCrudas.getInt("idCompras");
                this.valorActual++;
                return new Compra(this.conexion, this.cliente, idActual);
            } catch (Exception e) {
                System.out.println(e.getClass().getName() + ": " + e.getMessage());
            }
            return null;
        }
    }
}
