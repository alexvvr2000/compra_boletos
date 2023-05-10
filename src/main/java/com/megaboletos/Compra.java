package com.megaboletos;
import com.megaboletos.usuarios.ClassBuilder;
import com.megaboletos.usuarios.Cliente;
import com.megaboletos.usuarios.Evento;
import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            "values (?,?,?) returning idCompras;"
        );
        query.setInt(1, nuevaCompra.clientePorComprar.getIdUsuario());
        query.setInt(2, nuevaCompra.idEvento);
        query.setInt(3, nuevaCompra.idMetodoPago);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        nuevaCompra.conexion.commit();
        this.conexion = nuevaCompra.conexion;
        this.idCliente = nuevaCompra.clientePorComprar.getIdUsuario();
        this.idEvento = nuevaCompra.idEvento;
        this.idMetodoPago = nuevaCompra.idMetodoPago;
        this.precioFinal = nuevaCompra.precioFinal;
        this.idCliente = resultado.getInt("idCompras");
        this.pagado = false;
    }
    public Compra(Connection conexion, Cliente clienteUsado, int idCompra) throws Exception{
        if(!clienteUsado.existeCompra(idCompra)) throw new Exception("Compra no existe en base");
        PreparedStatement query = conexion.prepareStatement(
            "select " +
                "idcompras, idusuario, idevento, idmetodopago, " +
                "asientoscomprados, preciofinal, pagado " +
            "from compra " +
            "where idcompras = ?;"
        );
        query.setInt(1, idCompra);
        ResultSet resultado = query.executeQuery();
        resultado.next();
        this.idCompra = resultado.getInt("idcompras");
        this.idCliente = clienteUsado.getIdUsuario();
        this.idEvento = resultado.getInt("idevento");
        this.idMetodoPago = resultado.getInt("idmetodopago");
        Array arregloValores = resultado.getArray("asientoscomprados");
        this.precioFinal = resultado.getInt("preciofinal");
        this.pagado = resultado.getBoolean("pagado");
        boolean errorAgregando = Collections.addAll(
                this.asientos, (String[])arregloValores.getArray()
        );
        if(errorAgregando) throw new Exception("No se pudo obtener boletos comprados de base");
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
    public boolean estoPagado() {
        return this.pagado;
    }
    public static class Builder implements ClassBuilder<Compra> {
        private Cliente clientePorComprar;
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
    public static boolean estaPagado(Cliente cliente, int idCompra) throws Exception{
        return true;
    }
    public boolean agregarAsiento(String fila, int asiento) throws Exception{
        return true;
    }
}
