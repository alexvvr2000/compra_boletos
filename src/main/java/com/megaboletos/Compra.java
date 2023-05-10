package com.megaboletos;
import com.megaboletos.usuarios.ClassBuilder;
import com.megaboletos.usuarios.Cliente;
import java.sql.Connection;
import java.util.*;
public class Compra {
    private int idCliente = 0;
    private int idEvento = 0;
    private int idCompra = 0;
    private int idMetodoPago = 0;
    private int precioFinal = 0;
    private Connection conexion = null;
    final Map<String, ArrayList<Integer>> asientosComprados = new HashMap<String, ArrayList<Integer>>();
    private Compra(Cliente clientePorComprar, Builder nuevaCompra, int idMetodoPago) {

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
        private Map<String, ArrayList<Integer>> asientos = new HashMap<String, ArrayList<Integer>>();
        public Builder(Cliente clientePorComprar) {
            this.clientePorComprar = clientePorComprar;
        }
        public Builder agregarEvento(int idEvento) {
            this.idEvento = idEvento;
            return this;
        }
        public Builder metodoPagoUsado(int idMetodoPago){
            this.idMetodoPago = idMetodoPago;
            return this;
        }
        public Builder agregarAsiento(String fila, int asiento) {
            return this;
        }
        @Override
        public Compra crear() throws Exception{
            return new Compra(this.clientePorComprar, this, this.idEvento);
        }
        @Override
        public boolean camposValidos() {
            return false;
        }
        private boolean pagarAsientos() {
            return true;
        }
        private int calcularPrecioFinal(){
            return 0;
        }
    }
}
