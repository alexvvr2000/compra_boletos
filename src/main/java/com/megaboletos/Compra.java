package com.megaboletos;
import com.megaboletos.pagos.MetodoPago;
import com.megaboletos.pagos.PagoVisa;
import com.megaboletos.usuarios.ClassBuilder;
import com.megaboletos.usuarios.Cliente;
import com.megaboletos.usuarios.Evento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;
public class Compra {
    private int idCliente = 0;
    private int idEvento = 0;
    private int idCompra = 0;
    private int idMetodoPago = 0;
    private int precioFinal = 0;
    final Map<String, ArrayList<Integer>> asientosComprados = new HashMap<String, ArrayList<Integer>>();
    private Connection conexion = null;
    private Compra(Builder nuevaCompra) throws Exception {
        PreparedStatement query = nuevaCompra.conexion.prepareStatement("");
        this.conexion = nuevaCompra.conexion;
        this.idCliente = nuevaCompra.clientePorComprar.getIdUsuario();
        this.idEvento = nuevaCompra.idEvento;
        this.idMetodoPago = nuevaCompra.idMetodoPago;
        this.precioFinal = nuevaCompra.precioFinal;
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
    public Map<String, ArrayList<Integer>> getAsientos() {
        return this.asientosComprados;
    }
    public static class Builder implements ClassBuilder<Compra> {
        private Cliente clientePorComprar = null;
        private int idEvento = 0;
        private int idMetodoPago = 0;
        private int precioFinal = 0;
        private Connection conexion = null;
        private Map<String, ArrayList<Integer>> asientos = new HashMap<String, ArrayList<Integer>>();
        public Builder(Connection conexion, Cliente clientePorComprar) {
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
        public Builder agregarAsiento(String fila, int asiento) throws Exception{
            Evento eventoAgregado = new Evento(this.conexion, this.idEvento);
            if(eventoAgregado.eventoCancelado()) throw new Exception("Evento cancelado");
            if(!eventoAgregado.estaDisponible(fila, asiento)) throw new Exception("Asiento tomado");
            if(!this.asientos.containsKey(fila)) {
                this.asientos.put(fila, new ArrayList<Integer>());
            }
            this.asientos.get(fila).add(Integer.valueOf(asiento));
            this.precioFinal += eventoAgregado.precioAsiento(fila);
            return this;
        }
        @Override
        public Compra crear() throws Exception{
            if(!this.camposValidos()) throw new Exception("Campos faltantes o sin formato");
            Map<String, String> metodoPago = this.clientePorComprar.obtenerMetodoPago(this.idMetodoPago);
            return new Compra(this);
        }
        @Override
        public boolean camposValidos() {
            boolean idEventoAgregado = this.idEvento != 0;
            boolean idMetodoPagoAgregado = this.idMetodoPago != 0;
            boolean asientosValidos = !this.asientos.isEmpty();
            boolean precioFinalCalculado = this.precioFinal != 0;
            return
                idEventoAgregado &&
                idMetodoPagoAgregado &&
                asientosValidos &&
                precioFinalCalculado;
        }
    }
    public boolean pagar(int CVV) throws Exception{
        return true;
    }
    public static boolean estaPagado(Cliente cliente, int idCompra) throws Exception{
        return true;
    }
}
