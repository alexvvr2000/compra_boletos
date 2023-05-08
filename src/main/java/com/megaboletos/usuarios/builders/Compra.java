package com.megaboletos.usuarios.builders;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Currency;
public class Compra {
    private final int idCliente;
    private final int idEvento;
    private final int idCompra;
    private final Currency precioFinal;
    private final Connection conexion;
    final ArrayList<String> asientos = new ArrayList<String>();
    public Compra(Connection conexion,int idCompra) {
        this.idCompra = idCompra;
        this.conexion = conexion;
    }
    private Compra(Cliente clientePorComprar, Builder nuevaCompra) {

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
    public Currency getPrecioFinal() {
        return precioFinal;
    }
    public ArrayList<String> getAsientos() {
        return this.asientos;
    }
    public boolean pagar(int CVV, Cliente cliente){
        return true;
    }
    public static class Builder {
        private Cliente clientePorComprar;
        private int idEvento;
        private ArrayList<String> asientos = new ArrayList<String>();
        public Builder(Cliente clientePorComprar) {
            this.clientePorComprar = clientePorComprar;
        }
        public Builder agregarEvento() {
            return this;
        }
        public Builder agregarAsientos(ArrayList<String> asientos){
            return this;
        }
        public Compra comprar() {
            return new Compra(this.clientePorComprar, this);
        }
        private boolean pagarAsientos() {
            return true;
        }
        private Currency calcularPrecioFinal(){
        }
    }
}
